package com.slack.api.methods;

import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Async variant of {@link ChatStream} for {@link AsyncMethodsClient}.
 * <p>
 * This class buffers markdown text and flushes via chat.startStream / chat.appendStream, then finalizes via
 * chat.stopStream. Access to the mutable stream state is synchronized by this class.
 * <p>
 * Calls that perform Slack API requests are chained in invocation order. If a queued append or stop operation fails,
 * any markdown text drained for that operation is restored to the buffer before the next queued operation starts.
 * While stop is pending, new append calls are rejected.
 */
@Slf4j
public class AsyncChatStream {

    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;
    private final AsyncChatStreamProtocol protocol;

    private final ChatStreamBuffer buffer;
    private final ChatStreamLifecycle lifecycle = new ChatStreamLifecycle();
    private final AsyncChatStreamQueue queue = new AsyncChatStreamQueue();

    @Builder
    private AsyncChatStream(
            AsyncMethodsClient client,
            String channel,
            String threadTs,
            String recipientTeamId,
            String recipientUserId,
            Integer bufferSize
    ) {
        this.channel = channel;
        this.threadTs = threadTs;
        this.recipientTeamId = recipientTeamId;
        this.recipientUserId = recipientUserId;
        this.protocol = new AsyncChatStreamProtocol(client, channel, threadTs, recipientTeamId, recipientUserId);
        this.buffer = new ChatStreamBuffer(bufferSize != null ? bufferSize : 256);
    }

    public int getBufferSize() {
        return buffer.getBufferSize();
    }

    public synchronized String getStreamTs() {
        return lifecycle.getStreamTs();
    }

    /**
     * Append text to the stream.
     *
     * @param markdownText markdown text to append
     * @return a future that completes with a response if the buffer was flushed; completes with null if buffering
     * @throws NullPointerException if markdownText is null
     */
    public CompletableFuture<ChatAppendStreamResponse> append(String markdownText) {
        Objects.requireNonNull(markdownText, "markdownText");
        synchronized (this) {
            if (lifecycle.getState() == ChatStreamLifecycle.StreamState.COMPLETED || queue.isStopRequested()) {
                return failedFuture(new SlackChatStreamException("Cannot append to stream: stream state is " + lifecycle.getState()));
            }

            buffer.append(markdownText);

            if (buffer.isReadyToFlush()) {
                return enqueueFlushBuffer();
            }

            if (log.isDebugEnabled()) {
                log.debug("AsyncChatStream appended to buffer: bufferLength={}, bufferSize={}, channel={}, " +
                                "recipientTeamId={}, recipientUserId={}, threadTs={}",
                        buffer.length(), getBufferSize(), channel, recipientTeamId, recipientUserId, threadTs);
            }
            return CompletableFuture.completedFuture(null);
        }
    }

    public CompletableFuture<ChatStopStreamResponse> stop() {
        return stop(null, null, null);
    }

    public CompletableFuture<ChatStopStreamResponse> stop(String markdownText) {
        return stop(markdownText, null, null);
    }

    /**
     * Stop the stream and finalize the message. Any buffered text is sent with the stop request after previously
     * queued API calls finish.
     *
     * @param markdownText Additional text to append before stopping (can be null)
     * @param blocks       A list of blocks that will be rendered at the bottom of the finalized message (can be null)
     * @param metadata     Metadata to attach to the message (can be null)
     * @return a future that completes with the chat.stopStream API response
     */
    public CompletableFuture<ChatStopStreamResponse> stop(
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) {
        synchronized (this) {
            if (lifecycle.getState() == ChatStreamLifecycle.StreamState.COMPLETED || queue.isStopRequested()) {
                return failedFuture(new SlackChatStreamException("Cannot stop stream: stream state is " + lifecycle.getState()));
            }

            buffer.appendIfNotNull(markdownText);

            return queue.enqueueStop(() -> stopCurrentBuffer(blocks, metadata));
        }
    }

    private CompletableFuture<ChatAppendStreamResponse> enqueueFlushBuffer() {
        return queue.enqueueFlush(() -> flushCurrentBuffer());
    }

    private CompletableFuture<ChatAppendStreamResponse> flushCurrentBuffer() {
        String markdownText;
        synchronized (this) {
            markdownText = buffer.drain();
        }
        if (markdownText.length() == 0) {
            return CompletableFuture.completedFuture(null);
        }
        return flushBuffer(markdownText).handle((resp, error) -> {
            if (error != null) {
                synchronized (AsyncChatStream.this) {
                    buffer.restore(markdownText);
                }
                throw propagate(error);
            }
            return resp;
        });
    }

    private CompletableFuture<ChatStopStreamResponse> stopCurrentBuffer(
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) {
        String markdownText;
        synchronized (this) {
            markdownText = buffer.drain();
        }
        return stopStream(markdownText, blocks, metadata).handle((resp, error) -> {
            if (error != null) {
                synchronized (AsyncChatStream.this) {
                    buffer.restore(markdownText);
                    queue.clearStopRequested();
                }
                throw propagate(error);
            }
            return resp;
        });
    }

    private static RuntimeException propagate(Throwable cause) {
        if (cause instanceof CompletionException && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }
        return new CompletionException(cause);
    }

    private CompletableFuture<ChatAppendStreamResponse> flushBuffer(String markdownText) {
        String ts;
        synchronized (this) {
            ts = lifecycle.getStreamTs();
        }
        if (ts == null) {
            return protocol.startStream(markdownText)
                    .thenApply(startResponse -> {
                        if (!startResponse.isOk() || startResponse.getTs() == null) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to start stream: " + startResponse.getError());
                            ex.setStartResponse(startResponse);
                            throw ex;
                        }
                        synchronized (this) {
                            lifecycle.markStarted(startResponse.getTs());
                        }
                        return toAppendResponse(startResponse);
                    });
        } else {
            return protocol.appendStream(ts, markdownText)
                    .thenApply(resp -> {
                        if (!resp.isOk()) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to append to stream: " + resp.getError());
                            ex.getAppendResponses().add(resp);
                            throw ex;
                        }
                        return resp;
                    });
        }
    }

    private CompletableFuture<ChatStopStreamResponse> stopStream(
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) {
        return ensureStartedForStop().thenCompose(ignored -> {
            String ts;
            synchronized (this) {
                ts = lifecycle.getStreamTs();
            }
            return protocol.stopStream(ts, markdownText, blocks, metadata)
                    .thenApply(resp -> {
                        if (!resp.isOk()) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to stop stream: " + resp.getError());
                            ex.setStopResponse(resp);
                            throw ex;
                        }
                        synchronized (this) {
                            lifecycle.markCompleted();
                        }
                        return resp;
                    });
        });
    }

    private CompletableFuture<Void> ensureStartedForStop() {
        String ts;
        synchronized (this) {
            ts = lifecycle.getStreamTs();
        }
        if (ts != null) {
            return CompletableFuture.completedFuture(null);
        }
        return protocol.startStream(null)
                .thenApply(startResponse -> {
                    if (!startResponse.isOk() || startResponse.getTs() == null) {
                        SlackChatStreamException ex = new SlackChatStreamException(
                                "Failed to stop stream: stream not started - " + startResponse.getError());
                        ex.setStartResponse(startResponse);
                        throw ex;
                    }
                    synchronized (this) {
                        lifecycle.markStarted(startResponse.getTs());
                    }
                    return null;
                });
    }

    private ChatAppendStreamResponse toAppendResponse(ChatStartStreamResponse startResponse) {
        ChatAppendStreamResponse response = new ChatAppendStreamResponse();
        response.setOk(startResponse.isOk());
        response.setChannel(startResponse.getChannel());
        response.setTs(startResponse.getTs());
        response.setWarning(startResponse.getWarning());
        response.setError(startResponse.getError());
        return response;
    }

    private static <T> CompletableFuture<T> failedFuture(Throwable cause) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(cause);
        return future;
    }
}

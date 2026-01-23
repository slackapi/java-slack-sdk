package com.slack.api.methods;

import com.slack.api.methods.request.chat.ChatAppendStreamRequest;
import com.slack.api.methods.request.chat.ChatStartStreamRequest;
import com.slack.api.methods.request.chat.ChatStopStreamRequest;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Async variant of {@link ChatStreamHelper} for {@link AsyncMethodsClient}.
 * <p>
 * This helper buffers markdown text and flushes via chat.startStream / chat.appendStream, then finalizes via
 * chat.stopStream.
 * <p>
 *
 */
@Data
@Slf4j
@Builder
public class AsyncChatStreamHelper {

    private enum StreamState {
        STARTING,
        IN_PROGRESS,
        COMPLETED
    }

    private final AsyncMethodsClient client;
    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;

    @Builder.Default
    private final int bufferSize = 256;

    @Builder.Default
    private StringBuilder buffer = new StringBuilder();
    @Builder.Default
    private StreamState state = StreamState.STARTING;
    private String streamTs;

    /**
     * Append text to the stream.
     *
     * @param markdownText markdown text to append
     * @return a future that completes with a response if the buffer was flushed; completes with null if buffering
     */
    public CompletableFuture<ChatAppendStreamResponse> append(String markdownText) {
        if (state == StreamState.COMPLETED) {
            CompletableFuture<ChatAppendStreamResponse> f = new CompletableFuture<>();
            f.completeExceptionally(new SlackChatStreamException("Cannot append to stream: stream state is " + state));
            return f;
        }

        buffer.append(markdownText);

        if (buffer.length() >= bufferSize) {
            return flushBuffer();
        }

        if (log.isDebugEnabled()) {
            log.debug("AsyncChatStream appended to buffer: bufferLength={}, bufferSize={}, channel={}, " +
                            "recipientTeamId={}, recipientUserId={}, threadTs={}",
                    buffer.length(), bufferSize, channel, recipientTeamId, recipientUserId, threadTs);
        }
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<ChatStopStreamResponse> stop() {
        return stop(null, null, null);
    }

    public CompletableFuture<ChatStopStreamResponse> stop(String markdownText) {
        return stop(markdownText, null, null);
    }

    public CompletableFuture<ChatStopStreamResponse> stop(
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) {
        if (state == StreamState.COMPLETED) {
            CompletableFuture<ChatStopStreamResponse> f = new CompletableFuture<>();
            f.completeExceptionally(new SlackChatStreamException("Cannot stop stream: stream state is " + state));
            return f;
        }

        if (markdownText != null) {
            buffer.append(markdownText);
        }

        CompletableFuture<Void> ensureStarted;
        if (streamTs == null) {
            ensureStarted = client.chatStartStream(ChatStartStreamRequest.builder()
                            .channel(channel)
                            .threadTs(threadTs)
                            .recipientTeamId(recipientTeamId)
                            .recipientUserId(recipientUserId)
                            .build())
                    .thenApply(startResponse -> {
                        if (!startResponse.isOk() || startResponse.getTs() == null) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to stop stream: stream not started - " + startResponse.getError());
                            ex.setStartResponse(startResponse);
                            throw ex;
                        }
                        streamTs = startResponse.getTs();
                        state = StreamState.IN_PROGRESS;
                        return null;
                    });
        } else {
            ensureStarted = CompletableFuture.completedFuture(null);
        }

        return ensureStarted.thenCompose(ignored -> client.chatStopStream(ChatStopStreamRequest.builder()
                        .channel(channel)
                        .ts(streamTs)
                        .markdownText(buffer.toString())
                        .blocks(blocks)
                        .metadata(metadata)
                        .build())
                .thenApply(resp -> {
                    state = StreamState.COMPLETED;
                    return resp;
                }));
    }

    private CompletableFuture<ChatAppendStreamResponse> flushBuffer() {
        if (streamTs == null) {
            return client.chatStartStream(ChatStartStreamRequest.builder()
                            .channel(channel)
                            .threadTs(threadTs)
                            .recipientTeamId(recipientTeamId)
                            .recipientUserId(recipientUserId)
                            .markdownText(buffer.toString())
                            .build())
                    .thenApply(startResponse -> {
                        if (!startResponse.isOk()) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to start stream: " + startResponse.getError());
                            ex.setStartResponse(startResponse);
                            throw ex;
                        }
                        streamTs = startResponse.getTs();
                        state = StreamState.IN_PROGRESS;
                        ChatAppendStreamResponse synth = new ChatAppendStreamResponse();
                        synth.setOk(startResponse.isOk());
                        synth.setChannel(startResponse.getChannel());
                        synth.setTs(startResponse.getTs());
                        synth.setWarning(startResponse.getWarning());
                        synth.setError(startResponse.getError());
                        buffer.setLength(0);
                        return synth;
                    });
        } else {
            return client.chatAppendStream(ChatAppendStreamRequest.builder()
                            .channel(channel)
                            .ts(streamTs)
                            .markdownText(buffer.toString())
                            .build())
                    .thenApply(resp -> {
                        if (!resp.isOk()) {
                            SlackChatStreamException ex = new SlackChatStreamException(
                                    "Failed to append to stream: " + resp.getError());
                            ex.getAppendResponses().add(resp);
                            throw ex;
                        }
                        buffer.setLength(0);
                        return resp;
                    });
        }
    }
}



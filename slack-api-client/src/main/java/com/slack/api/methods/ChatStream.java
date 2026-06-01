package com.slack.api.methods;

import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * A class for streaming markdown text into a conversation using the chat streaming APIs.
 * <p>
 * This class provides a convenient interface for the chat.startStream, chat.appendStream, and chat.stopStream API
 * methods, with automatic buffering and state management.
 * <p>
 * This class is not thread-safe. Use {@link AsyncChatStream} when appends and stop calls may be invoked from
 * multiple threads.
 * <p>
 * Typical usage is to use the {@link MethodsClient#chatStream} method:
 *
 * <pre>
 * {@code
 * MethodsClient client = Slack.getInstance().methods(token);
 * ChatStream stream = client.chatStream(req -> req
 *     .channel("C0123456789")
 *     .threadTs("1700000001.123456")
 *     .recipientTeamId("T0123456789")
 *     .recipientUserId("U0123456789")
 *     .bufferSize(100));
 *
 * stream.append("**hello wo");
 * stream.append("rld!**");
 * ChatStopStreamResponse response = stream.stop();
 * }
 * </pre>
 */
@Slf4j
public class ChatStream {

    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;
    private final ChatStreamProtocol protocol;

    /**
     * The length of markdown_text to buffer in-memory before calling a method.
     * Increasing this value decreases the number of method calls made for the same amount of text,
     * which is useful to avoid rate limits.
     * Default is 256.
     */
    private final ChatStreamBuffer buffer;
    private final ChatStreamLifecycle lifecycle = new ChatStreamLifecycle();

    @Builder
    private ChatStream(
            MethodsClient client,
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
        this.protocol = new ChatStreamProtocol(client, channel, threadTs, recipientTeamId, recipientUserId);
        this.buffer = new ChatStreamBuffer(bufferSize != null ? bufferSize : 256);
    }

    public int getBufferSize() {
        return buffer.getBufferSize();
    }

    public String getStreamTs() {
        return lifecycle.getStreamTs();
    }

    /**
     * Append text to the stream.
     * <p>
     * This method can be called multiple times. After the stream is stopped, this method cannot be called.
     *
     * @param markdownText Accepts message text formatted in markdown. Limit this field to 12,000 characters.
     *                     This text is what will be appended to the message received so far.
     * @return ChatAppendStreamResponse if the buffer was flushed; null if the text was only buffered
     * @throws SlackChatStreamException if the stream is already completed or an API error occurs
     * @throws IOException              if a network error occurs
     * @throws SlackApiException        if a Slack API error occurs
     * @throws NullPointerException     if markdownText is null
     */
    public ChatAppendStreamResponse append(String markdownText) throws IOException, SlackApiException {
        Objects.requireNonNull(markdownText, "markdownText");
        lifecycle.verifyAppendable();

        buffer.append(markdownText);

        if (buffer.isReadyToFlush()) {
            return flushBuffer();
        }

        if (log.isDebugEnabled()) {
            log.debug("ChatStream appended to buffer: bufferLength={}, bufferSize={}, channel={}, " +
                            "recipientTeamId={}, recipientUserId={}, threadTs={}",
                        buffer.length(), getBufferSize(), channel, recipientTeamId, recipientUserId, threadTs);
        }

        return null;
    }

    /**
     * Stop the stream and finalize the message.
     *
     * @return ChatStopStreamResponse from the chat.stopStream API call
     * @throws SlackChatStreamException if the stream is already completed or an API error occurs
     * @throws IOException              if a network error occurs
     * @throws SlackApiException        if a Slack API error occurs
     */
    public ChatStopStreamResponse stop() throws IOException, SlackApiException {
        return stop(null, null, null);
    }

    /**
     * Stop the stream and finalize the message.
     *
     * @param markdownText Additional text to append before stopping
     * @return ChatStopStreamResponse from the chat.stopStream API call
     * @throws SlackChatStreamException if the stream is already completed or an API error occurs
     * @throws IOException              if a network error occurs
     * @throws SlackApiException        if a Slack API error occurs
     */
    public ChatStopStreamResponse stop(String markdownText) throws IOException, SlackApiException {
        return stop(markdownText, null, null);
    }

    /**
     * Stop the stream and finalize the message.
     *
     * @param markdownText Additional text to append before stopping (can be null)
     * @param blocks       A list of blocks that will be rendered at the bottom of the finalized message (can be null)
     * @param metadata     Metadata to attach to the message (can be null)
     * @return ChatStopStreamResponse from the chat.stopStream API call
     * @throws SlackChatStreamException if the stream is already completed or an API error occurs
     * @throws IOException              if a network error occurs
     * @throws SlackApiException        if a Slack API error occurs
     */
    public ChatStopStreamResponse stop(
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) throws IOException, SlackApiException {
        lifecycle.verifyStoppable();

        buffer.appendIfNotNull(markdownText);

        // If the stream hasn't started yet, start it first
        if (!lifecycle.isStarted()) {
            ChatStartStreamResponse startResponse = protocol.startStream(null);

            if (!startResponse.isOk() || startResponse.getTs() == null) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to stop stream: stream not started - " + startResponse.getError());
                ex.setStartResponse(startResponse);
                throw ex;
            }

            lifecycle.markStarted(startResponse.getTs());
        }

        ChatStopStreamResponse response = protocol.stopStream(
                lifecycle.getStreamTs(), buffer.getMarkdownText(), blocks, metadata);

        if (!response.isOk()) {
            SlackChatStreamException ex = new SlackChatStreamException(
                    "Failed to stop stream: " + response.getError());
            ex.setStopResponse(response);
            throw ex;
        }

        lifecycle.markCompleted();
        return response;
    }

    /**
     * Flush the internal buffer by making appropriate API calls.
     *
     * @return ChatAppendStreamResponse from the API call (or a synthesized response for the first call)
     * @throws IOException       if a network error occurs
     * @throws SlackApiException if a Slack API error occurs
     */
    private ChatAppendStreamResponse flushBuffer() throws IOException, SlackApiException {
        ChatAppendStreamResponse response;

        if (!lifecycle.isStarted()) {
            // First flush - start the stream
            ChatStartStreamResponse startResponse = protocol.startStream(buffer.getMarkdownText());

            if (!startResponse.isOk() || startResponse.getTs() == null) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to start stream: " + startResponse.getError());
                ex.setStartResponse(startResponse);
                throw ex;
            }

            lifecycle.markStarted(startResponse.getTs());

            response = toAppendResponse(startResponse);
        } else {
            // Subsequent flush - append to stream
            response = protocol.appendStream(lifecycle.getStreamTs(), buffer.getMarkdownText());

            if (!response.isOk()) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to append to stream: " + response.getError());
                ex.getAppendResponses().add(response);
                throw ex;
            }
        }

        // Clear the buffer
        buffer.clear();
        return response;
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
}

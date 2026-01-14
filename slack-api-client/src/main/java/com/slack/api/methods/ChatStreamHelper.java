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

import java.io.IOException;
import java.util.List;

/**
 * A helper class for streaming markdown text into a conversation using the chat streaming APIs.
 * <p>
 * This class provides a convenient interface for the chat.startStream, chat.appendStream, and chat.stopStream API
 * methods, with automatic buffering and state management.
 * <p>
 * Typical usage is to build a token-bound {@link MethodsClient} first, then construct this helper:
 *
 * <pre>
 * {@code
 * MethodsClient client = Slack.getInstance().methods(token);
 * ChatStreamHelper stream = ChatStreamHelper.builder()
 *     .client(client)
 *     .channel("C0123456789")
 *     .threadTs("1700000001.123456")
 *     .recipientTeamId("T0123456789")
 *     .recipientUserId("U0123456789")
 *     .bufferSize(100)
 *     .build();
 *
 * stream.append("**hello wo");
 * stream.append("rld!**");
 * ChatStopStreamResponse response = stream.stop();
 * }
 * </pre>
 */
@Data
@Slf4j
@Builder
public class ChatStreamHelper {

    /**
     * The state of the chat stream.
     */
    public enum State {
        STARTING,
        IN_PROGRESS,
        COMPLETED
    }

    private final MethodsClient client;
    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;

    /**
     * The length of markdown_text to buffer in-memory before calling a method.
     * Increasing this value decreases the number of method calls made for the same amount of text,
     * which is useful to avoid rate limits.
     * Default is 100.
     */
    @Builder.Default
    private final int bufferSize = 256;

    // Mutable state (not thread-safe)
    @Builder.Default
    private StringBuilder buffer = new StringBuilder();
    @Builder.Default
    private State state = State.STARTING;
    private String streamTs;

    /**
     * Append text to the stream.
     * <p>
     * This method can be called multiple times. After the stream is stopped, this method cannot be called.
     *
     * @param markdownText Accepts message text formatted in markdown. Limit this field to 12,000 characters.
     *                     This text is what will be appended to the message received so far.
     * @return ChatAppendStreamResponse if the buffer was flushed, null if buffering
     * @throws SlackChatStreamException if the stream is already completed or an API error occurs
     * @throws IOException              if a network error occurs
     * @throws SlackApiException        if a Slack API error occurs
     */
    public ChatAppendStreamResponse append(String markdownText) throws IOException, SlackApiException {
        if (state == State.COMPLETED) {
            throw new SlackChatStreamException("Cannot append to stream: stream state is " + state);
        }

        buffer.append(markdownText);

        if (buffer.length() >= bufferSize) {
            return flushBuffer();
        }

        if (log.isDebugEnabled()) {
            log.debug("ChatStream appended to buffer: bufferLength={}, bufferSize={}, channel={}, " +
                            "recipientTeamId={}, recipientUserId={}, threadTs={}",
                    buffer.length(), bufferSize, channel, recipientTeamId, recipientUserId, threadTs);
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
        if (state == State.COMPLETED) {
            throw new SlackChatStreamException("Cannot stop stream: stream state is " + state);
        }

        if (markdownText != null) {
            buffer.append(markdownText);
        }

        // If the stream hasn't started yet, start it first
        if (streamTs == null) {
            ChatStartStreamResponse startResponse = client.chatStartStream(ChatStartStreamRequest.builder()
                    .channel(channel)
                    .threadTs(threadTs)
                    .recipientTeamId(recipientTeamId)
                    .recipientUserId(recipientUserId)
                    .build());

            if (!startResponse.isOk() || startResponse.getTs() == null) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to stop stream: stream not started - " + startResponse.getError());
                ex.setStartResponse(startResponse);
                throw ex;
            }

            streamTs = startResponse.getTs();
            state = State.IN_PROGRESS;
        }

        ChatStopStreamResponse response = client.chatStopStream(ChatStopStreamRequest.builder()
                .channel(channel)
                .ts(streamTs)
                .markdownText(buffer.toString())
                .blocks(blocks)
                .metadata(metadata)
                .build());

        state = State.COMPLETED;
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

        if (streamTs == null) {
            // First flush - start the stream
            ChatStartStreamResponse startResponse = client.chatStartStream(ChatStartStreamRequest.builder()
                    .channel(channel)
                    .threadTs(threadTs)
                    .recipientTeamId(recipientTeamId)
                    .recipientUserId(recipientUserId)
                    .markdownText(buffer.toString())
                    .build());

            if (!startResponse.isOk()) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to start stream: " + startResponse.getError());
                ex.setStartResponse(startResponse);
                throw ex;
            }

            streamTs = startResponse.getTs();
            state = State.IN_PROGRESS;

            // Create a response object to return (mimicking the append response structure)
            response = new ChatAppendStreamResponse();
            response.setOk(startResponse.isOk());
            response.setChannel(startResponse.getChannel());
            response.setTs(startResponse.getTs());
            response.setWarning(startResponse.getWarning());
            response.setError(startResponse.getError());
        } else {
            // Subsequent flush - append to stream
            response = client.chatAppendStream(ChatAppendStreamRequest.builder()
                    .channel(channel)
                    .ts(streamTs)
                    .markdownText(buffer.toString())
                    .build());

            if (!response.isOk()) {
                SlackChatStreamException ex = new SlackChatStreamException(
                        "Failed to append to stream: " + response.getError());
                ex.getAppendResponses().add(response);
                throw ex;
            }
        }

        // Clear the buffer
        buffer.setLength(0);
        return response;
    }
}



package com.slack.api.methods;

import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an error that occurred during chat streaming operations.
 */
public class SlackChatStreamException extends RuntimeException {

    @Getter
    @Setter
    private ChatStartStreamResponse startResponse;
    @Getter
    private final List<ChatAppendStreamResponse> appendResponses = new ArrayList<>();
    @Getter
    @Setter
    private ChatStopStreamResponse stopResponse;

    public SlackChatStreamException(String message) {
        super(message);
    }

    public SlackChatStreamException(String message, Throwable cause) {
        super(message, cause);
    }
}

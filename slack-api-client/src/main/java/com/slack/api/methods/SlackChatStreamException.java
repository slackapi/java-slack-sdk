package com.slack.api.methods;

import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an error that occurred during chat streaming operations.
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SlackChatStreamException extends RuntimeException {

    private ChatStartStreamResponse startResponse;
    private final List<ChatAppendStreamResponse> appendResponses = new ArrayList<>();
    private ChatStopStreamResponse stopResponse;

    public SlackChatStreamException(String message) {
        super(message);
    }

    public SlackChatStreamException(String message, Throwable cause) {
        super(message, cause);
    }
}


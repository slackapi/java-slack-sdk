package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/chat.appendStream
 */
@Data
@Builder
public class ChatAppendStreamRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * An encoded ID that represents a channel, private group, or DM.
     */
    private String channel;

    /**
     * The timestamp of the streaming message.
     */
    private String ts;

    /**
     * Accepts message text formatted in markdown. Limit this field to 12,000 characters. This text is what will be appended to the message received so far.
     */
    private String markdownText;
}

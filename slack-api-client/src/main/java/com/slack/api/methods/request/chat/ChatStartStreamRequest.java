package com.slack.api.methods.request.chat;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/chat.startStream
 */
@Data
@Builder
public class ChatStartStreamRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * An encoded ID that represents a channel, private group, or DM.
     */
    private String channel;

    /**
     * Provide another message's `ts` value to reply to. Streamed messages should always be replies to a user request.
     */
    private String threadTs;

    /**
     * Accepts message text formatted in markdown. Limit this field to 12,000 characters.
     */
    private String markdownText;

    /**
     * The encoded ID of the user to receive the streaming text. Required when streaming to channels.
     */
    private String recipientUserId;

    /**
     * The encoded ID of the team the user receiving the streaming text belongs to. Required when streaming to channels.
     */
    private String recipientTeamId;
}

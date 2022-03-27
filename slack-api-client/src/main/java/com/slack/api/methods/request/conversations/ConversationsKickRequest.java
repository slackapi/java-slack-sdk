package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.kick
 */
@Data
@Builder
public class ConversationsKickRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * ID of conversation to remove user from.
     */
    private String channel;

    /**
     * User ID to be removed.
     */
    private String user;

}

package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.info
 */
@Data
@Builder
public class ConversationsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    /**
     * Conversation ID to learn more about
     */
    private String channel;

    /**
     * Set this to `true` to receive the locale for this conversation. Defaults to `false`
     */
    private boolean includeLocale;

    /**
     * Set to true to include the member count for the specified conversation. Defaults to false
     */
    private boolean includeNumMembers;

}
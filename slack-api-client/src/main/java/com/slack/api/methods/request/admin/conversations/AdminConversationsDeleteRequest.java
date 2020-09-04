package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.delete
 */
@Data
@Builder
public class AdminConversationsDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to delete.
     */
    private String channelId;

}

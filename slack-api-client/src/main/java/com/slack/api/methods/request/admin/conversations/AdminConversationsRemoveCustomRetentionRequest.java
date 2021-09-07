package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.removeCustomRetention
 */
@Data
@Builder
public class AdminConversationsRemoveCustomRetentionRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to set the retention policy for.
     */
    private String channelId;

}

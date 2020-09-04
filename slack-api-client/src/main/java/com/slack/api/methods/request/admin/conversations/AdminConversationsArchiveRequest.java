package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.archive
 */
@Data
@Builder
public class AdminConversationsArchiveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to archive.
     */
    private String channelId;
}

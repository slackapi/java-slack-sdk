package com.slack.api.methods.request.admin.conversations.restrict_access;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.restrictAccess.removeGroup
 */
@Data
@Builder
public class AdminConversationsRestrictAccessRemoveGroupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to remove a whitelisted group for.
     */
    private String channelId;

    /**
     * The IDP Group ID to remove from the private channel whitelist.
     */
    private String groupId;

    /**
     * The workspace where the IDP Group and channel exist.
     */
    private String teamId;

}

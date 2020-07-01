package com.slack.api.methods.request.admin.conversations.restrict_access;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.restrictAccess.listGroups
 */
@Data
@Builder
public class AdminConversationsRestrictAccessListGroupsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String channelId;

    /**
     * The workspace where the channel exists.
     * This argument is required for channels only tied to one workspace,
     * and optional for channels that are shared across an organization.
     */
    private String teamId;

}

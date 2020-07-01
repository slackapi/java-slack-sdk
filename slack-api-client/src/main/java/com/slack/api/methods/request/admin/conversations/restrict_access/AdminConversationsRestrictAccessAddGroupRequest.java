package com.slack.api.methods.request.admin.conversations.restrict_access;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.restrictAccess.addGroup
 */
@Data
@Builder
public class AdminConversationsRestrictAccessAddGroupRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to whitelist a group for.
     */
    private String channelId;

    /**
     * The IDP Group ID to whitelist for the private channel.
     */
    private String groupId;

    /**
     * The workspace where the IDP Group and channel exist.
     */
    private String teamId;

}

package com.slack.api.methods.request.admin.conversations.whitelist;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated
@Data
@Builder
public class AdminConversationsWhitelistListGroupsLinkedToChannelRequest implements SlackApiRequest {

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

package com.slack.api.methods.request.admin.conversations.whitelist;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.conversations.whitelist.remove
 */
@Data
@Builder
public class AdminConversationsWhitelistRemoveRequest implements SlackApiRequest {

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

package com.slack.api.methods.request.admin.conversations.whitelist;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.conversations.whitelist.add
 */
@Data
@Builder
public class AdminConversationsWhitelistAddRequest implements SlackApiRequest {

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

package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.disconnectShared
 */
@Data
@Builder
public class AdminConversationsDisconnectSharedRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to be disconnected from some workspaces.
     */
    private String channelId;

    /**
     * The team to be removed from the channel. Currently only a single team id can be specified.
     */
    private List<String> leavingTeamIds;

}

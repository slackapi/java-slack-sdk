package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.setTeams
 */
@Data
@Builder
public class AdminConversationsSetTeamsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The encoded channel_id to add or remove to workspaces.
     */
    private String channelId;

    /**
     * True if channel has to be converted to an org channel
     */
    private Boolean orgChannel;

    /**
     * The list of workspaces to which the channel should be shared. Not required if the channel is being shared orgwide.
     */
    private List<String> targetTeamIds;

    /**
     * The workspace to which the channel belongs. Omit this argument if the channel is a cross-workspace shared channel.
     */
    private String teamId;

}

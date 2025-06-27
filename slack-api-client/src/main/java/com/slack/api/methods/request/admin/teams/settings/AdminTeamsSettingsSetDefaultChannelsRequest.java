package com.slack.api.methods.request.admin.teams.settings;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.teams.settings.setDefaultChannels
 */
@Data
@Builder
public class AdminTeamsSettingsSetDefaultChannelsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * ID for the workspace to set the default channel for.
     */
    private String teamId;

    /**
     * A list of channel IDs.
     */
    private List<String> channelIds;
}

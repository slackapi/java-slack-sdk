package com.slack.api.methods.request.admin.teams.settings;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.teams.settings.setIcon
 */
@Data
@Builder
public class AdminTeamsSettingsSetIconRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * ID for the workspace to set the icon for.
     */
    private String teamId;

    private String imageUrl;

}

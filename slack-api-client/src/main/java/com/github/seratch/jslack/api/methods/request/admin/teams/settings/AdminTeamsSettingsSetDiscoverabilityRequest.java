package com.github.seratch.jslack.api.methods.request.admin.teams.settings;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.teams.settings.setDiscoverability
 */
@Data
@Builder
public class AdminTeamsSettingsSetDiscoverabilityRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Workspace Id.
     */
    private String teamId;

    /**
     * This workspace's discovery setting. It must be set to one of open, invite_only, closed, or unlisted.
     */
    private String discoverability;

}

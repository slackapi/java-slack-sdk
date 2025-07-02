package com.slack.api.methods.request.admin.teams.settings;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.teams.settings.setDiscoverability
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

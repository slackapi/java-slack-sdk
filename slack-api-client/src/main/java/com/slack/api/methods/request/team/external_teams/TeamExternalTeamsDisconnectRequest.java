package com.slack.api.methods.request.team.external_teams;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/team.externalTeams.disconnect
 */
@Data
@Builder
public class TeamExternalTeamsDisconnectRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * The encoded team ID of the target team.
     */
    private String targetTeam;
}

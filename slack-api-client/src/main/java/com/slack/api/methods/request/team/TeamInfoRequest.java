package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/team.info
 */
@Data
@Builder
public class TeamInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `team:read`
     */
    private String token;

    /**
     * Team to get info on, if omitted, will return information about the current team. Will only return team that the authenticated token is allowed to see through external shared channels
     */
    private String team;

    /**
     * Query by domain instead of team (only when team is null). This only works for domains in the same enterprise as the querying team token. This also expects the domain to belong to a team and not the enterprise itself.
     */
    private String domain;

}

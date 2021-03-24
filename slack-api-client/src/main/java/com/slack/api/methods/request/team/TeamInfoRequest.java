package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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

}

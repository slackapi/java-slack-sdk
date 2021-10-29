package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamPreferencesListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `team.preferences:read`
     */
    private String token;

}
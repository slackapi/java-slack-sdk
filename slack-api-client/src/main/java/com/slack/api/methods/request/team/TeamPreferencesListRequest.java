package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/team.preferences.list
 */
@Data
@Builder
public class TeamPreferencesListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `team.preferences:read`
     */
    private String token;

}
package com.slack.api.methods.request.team.profile;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/team.profile.get
 */
@Data
@Builder
public class TeamProfileGetRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users.profile:read`
     */
    private String token;

    /**
     * Filter by visibility.
     */
    private String visibility;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/team.billing.info
 */
@Data
@Builder
public class TeamBillingInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `team.billing:read`
     */
    private String token;

}
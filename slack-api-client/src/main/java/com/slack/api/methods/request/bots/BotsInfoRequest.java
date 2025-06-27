package com.slack.api.methods.request.bots;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/bots.info
 */
@Data
@Builder
public class BotsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:read`
     */
    private String token;

    /**
     * Bot user to get info on
     */
    private String bot;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
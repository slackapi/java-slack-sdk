package com.slack.api.app_backend.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The basic configuration for Slack apps.
 *
 * @see <a href="https://api.slack.com/apps">The list of your Slack apps</a>
 * @see <a href="https://api.slack.com/docs/oauth">Slack OAuth</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackAppConfig {
    // https://api.slack.com/docs/oauth
    private String clientId;
    // https://api.slack.com/docs/oauth
    private String clientSecret;
    // https://api.slack.com/docs/oauth
    private String redirectUri;
}

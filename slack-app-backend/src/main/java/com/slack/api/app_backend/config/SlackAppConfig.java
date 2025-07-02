package com.slack.api.app_backend.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The basic configuration for Slack apps.
 *
 * @see <a href="https://api.slack.com/apps">The list of your Slack apps</a>
 * @see <a href="https://docs.slack.dev/authentication/installing-with-oauth">Slack OAuth</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackAppConfig {
    // https://docs.slack.dev/authentication/installing-with-oauth
    private String clientId;
    // https://docs.slack.dev/authentication/installing-with-oauth
    private String clientSecret;
    // https://docs.slack.dev/authentication/installing-with-oauth
    private String redirectUri;
}

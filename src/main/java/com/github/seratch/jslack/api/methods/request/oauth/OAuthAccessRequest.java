package com.github.seratch.jslack.api.methods.request.oauth;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/docs/oauth
 */
@Data
@Builder
public class OAuthAccessRequest implements SlackApiRequest {

    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
}
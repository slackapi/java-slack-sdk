package com.github.seratch.jslack.api.methods.request.oauth;

import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/docs/oauth
 */
@Data
@Builder
public class OAuthAccessRequest {

    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
}
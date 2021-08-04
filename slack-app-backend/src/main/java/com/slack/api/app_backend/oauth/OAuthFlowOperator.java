package com.slack.api.app_backend.oauth;

import com.slack.api.Slack;
import com.slack.api.app_backend.config.SlackAppConfig;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.oauth.OAuthAccessRequest;
import com.slack.api.methods.request.oauth.OAuthV2AccessRequest;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;

import java.io.IOException;

public class OAuthFlowOperator {

    private final Slack slack;
    private final SlackAppConfig config;

    public OAuthFlowOperator(Slack slack, SlackAppConfig config) {
        this.slack = slack;
        this.config = config;
    }

    public OAuthAccessResponse callOAuthAccessMethod(VerificationCodePayload payload) throws IOException, SlackApiException {
        OAuthAccessRequest.OAuthAccessRequestBuilder apiRequest = OAuthAccessRequest.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .code(payload.getCode());
        if (config.getRedirectUri() != null) {
            apiRequest = apiRequest.redirectUri(config.getRedirectUri());
        }
        return slack.methods().oauthAccess(apiRequest.build());
    }

    public OAuthV2AccessResponse callOAuthV2AccessMethod(VerificationCodePayload payload) throws IOException, SlackApiException {
        OAuthV2AccessRequest.OAuthV2AccessRequestBuilder apiRequest = OAuthV2AccessRequest.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .code(payload.getCode());
        if (config.getRedirectUri() != null) {
            apiRequest = apiRequest.redirectUri(config.getRedirectUri());
        }
        return slack.methods().oauthV2Access(apiRequest.build());
    }

    public OpenIDConnectTokenResponse callOpenIDConnectToken(VerificationCodePayload payload) throws IOException, SlackApiException {
        return slack.methods().openIDConnectToken(r -> r
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .code(payload.getCode())
                .redirectUri(config.getRedirectUri())
        );
    }

}

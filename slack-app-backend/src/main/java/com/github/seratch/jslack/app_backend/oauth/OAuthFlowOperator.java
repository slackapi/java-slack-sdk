package com.github.seratch.jslack.app_backend.oauth;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.oauth.OAuthAccessRequest;
import com.slack.api.methods.request.oauth.OAuthV2AccessRequest;
import com.slack.api.methods.response.oauth.OAuthAccessResponse;
import com.slack.api.methods.response.oauth.OAuthV2AccessResponse;
import com.github.seratch.jslack.app_backend.config.SlackAppConfig;
import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;

import java.io.IOException;

public class OAuthFlowOperator {

    private final Slack slack;
    private final SlackAppConfig config;

    public OAuthFlowOperator(Slack slack, SlackAppConfig config) {
        this.slack = slack;
        this.config = config;
    }

    public OAuthAccessResponse callOAuthAccessMethod(VerificationCodePayload payload) throws IOException, SlackApiException {
        return slack.methods().oauthAccess(OAuthAccessRequest.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .code(payload.getCode())
                .redirectUri(config.getRedirectUri())
                .build());
    }

    public OAuthV2AccessResponse callOAuthV2AccessMethod(VerificationCodePayload payload) throws IOException, SlackApiException {
        return slack.methods().oauthV2Access(OAuthV2AccessRequest.builder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .code(payload.getCode())
                .redirectUri(config.getRedirectUri())
                .build());
    }

}

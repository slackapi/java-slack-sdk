package com.github.seratch.jslack.lightning.request;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.context.Context;
import com.github.seratch.jslack.lightning.context.builtin.OAuthCallbackContext;
import lombok.ToString;

@ToString
public abstract class Request<CTX extends Context> {

    private String clientIpAddress;

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public abstract CTX getContext();

    public void updateContext(AppConfig config) {
        // To use the properly configured Web API client
        getContext().setSlack(config.getSlack());

        // When the app is a distributed app, Lightning enables MultiTeamsAuthorization
        if (config.isDistributedApp() == false
                && getContext().getBotToken() == null
                && config.getSingleTeamBotToken() != null) {
            getContext().setBotToken(config.getSingleTeamBotToken());
        }

        if (config.isOAuthCallbackEnabled() && getContext() instanceof OAuthCallbackContext) {
            OAuthCallbackContext ctx = (OAuthCallbackContext) getContext();
            ctx.setOauthCompletionUrl(config.getOauthCompletionUrl());
            ctx.setOauthCancellationUrl(config.getOauthCancellationUrl());
        }
    }

    public abstract RequestType getRequestType();

    public abstract String getRequestBodyAsString();

    public abstract RequestHeaders getHeaders();

    public boolean isValid(SlackSignature.Verifier verifier) {
        return isValid(verifier, System.currentTimeMillis());
    }

    public boolean isValid(SlackSignature.Verifier verifier, long nowInMillis) {
        String requestTimestamp = getHeaders().get(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
        String requestSignature = getHeaders().get(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
        return verifier.isValid(requestTimestamp, getRequestBodyAsString(), requestSignature, nowInMillis);
    }

}

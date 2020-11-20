package com.slack.api.bolt.request;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.builtin.OAuthCallbackContext;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Http Request from the Slack API server.
 *
 * @param <CTX> context
 */
@ToString
public abstract class Request<CTX extends Context> {

    /**
     * The client IP address of the Slack API server.
     */
    private String clientIpAddress;

    private final ConcurrentMap<String, List<String>> queryString = new ConcurrentHashMap<>();

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    /**
     * The context behind this request.
     */
    public abstract CTX getContext();

    /**
     * Set the sufficient information to the underlying context.
     */
    public void updateContext(AppConfig config) {
        // To use the properly configured Web API client
        getContext().setSlack(config.getSlack());

        // When the app is a distributed app, Bolt enables MultiTeamsAuthorization
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

    public Map<String, List<String>> getQueryString() {
        return queryString;
    }

    public abstract String getRequestBodyAsString();

    public abstract RequestHeaders getHeaders();

    public abstract String getResponseUrl();

    /**
     * Verifies if the signature is valid.
     *
     * @param verifier the verifier
     * @return true if valid
     */
    public boolean isValid(SlackSignature.Verifier verifier) {
        return isValid(verifier, System.currentTimeMillis());
    }

    /**
     * Verifies if the signature is valid.
     *
     * @param verifier    the verifier
     * @param nowInMillis current timestamp
     * @return true if valid
     */
    public boolean isValid(SlackSignature.Verifier verifier, long nowInMillis) {
        String requestTimestamp = getHeaders().getFirstValue(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
        String requestSignature = getHeaders().getFirstValue(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
        return verifier.isValid(requestTimestamp, getRequestBodyAsString(), requestSignature, nowInMillis);
    }

    private boolean socketMode;

    public boolean isSocketMode() {
        return socketMode;
    }

    public void setSocketMode(boolean socketMode) {
        this.socketMode = socketMode;
    }

}

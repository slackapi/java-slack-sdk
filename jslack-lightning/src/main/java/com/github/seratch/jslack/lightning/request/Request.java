package com.github.seratch.jslack.lightning.request;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.context.Context;
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
        getContext().setSlack(config.getSlack());
        if (getContext().getBotToken() == null && config.getSingleTeamBotToken() != null) {
            getContext().setBotToken(config.getSingleTeamBotToken());
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

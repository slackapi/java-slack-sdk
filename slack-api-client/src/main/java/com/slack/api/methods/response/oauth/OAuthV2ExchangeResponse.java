package com.slack.api.methods.response.oauth;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * https://api.slack.com/methods/oauth.v2.exchange
 */
@Data
public class OAuthV2ExchangeResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String appId;
    private AuthedUser authedUser;
    private String scope;
    private String tokenType; // "bot"
    private String accessToken; // xoxb-xxx-yyy
    private String refreshToken; // only when enabling token rotation
    private Integer expiresIn; // in seconds; only when enabling token rotation
    private String botUserId;
    private Team team;
    private Enterprise enterprise;
    private boolean isEnterpriseInstall;
    private IncomingWebhook incomingWebhook;

    @Data
    public static class AuthedUser {
        private String id;
        private String scope;
        private String tokenType; // "user"
        private String accessToken; // xoxp-xxx-yyy
        private String refreshToken; // only when enabling token rotation
        private Integer expiresIn; // in seconds; only when enabling token rotation
    }

    @Data
    public static class Team {
        private String id;
        private String name;
    }

    @Data
    public static class Enterprise {
        private String id;
        private String name;
    }

    @Data
    public static class IncomingWebhook {
        private String url;
        private String channel;
        private String channelId;
        private String configurationUrl;
    }

    private ErrorResponseMetadata responseMetadata;
}

package com.github.seratch.jslack.api.methods.response.oauth;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class OAuthTokenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String accessToken;
    private String scope;
    private String teamName;
    private String teamId;
    private String userId;
    private IncomingWebhook incomingWebhook;
    private Bot bot;

    @Data
    public static class IncomingWebhook {
        private String url;
        private String channel;
        private String channelId;
        private String configurationUrl;
    }

    @Data
    public static class Bot {
        private String botUserId;
        private String botAccessToken;
    }
}

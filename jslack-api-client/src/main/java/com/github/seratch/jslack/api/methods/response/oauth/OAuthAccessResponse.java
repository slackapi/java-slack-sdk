package com.github.seratch.jslack.api.methods.response.oauth;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class OAuthAccessResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String tokenType;
    private String accessToken;
    private String scope;
    private String enterpriseId;
    private String teamName;
    private String teamId;
    private String userId;
    private IncomingWebhook incomingWebhook;
    private Bot bot;

    private AuthorizingUser authorizingUser;
    private InstallerUser installerUser;
    @Deprecated // for workspace apps
    private Scopes scopes;

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

    @Data
    public static class AuthorizingUser {
        private String userId;
        private String appHome;
    }

    @Data
    public static class InstallerUser {
        private String userId;
        private String appHome;
    }

    @Data
    public static class Scopes {
        private List<String> appHome;
        private List<String> team;
        private List<String> channel;
        private List<String> group;
        private List<String> mpim;
        private List<String> im;
        private List<String> user;
    }

}

package com.slack.api.methods.response.oauth;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;

@Data
public class OAuthAccessResponse implements SlackApiTextResponse {

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

    @Deprecated // for workspace apps
    private AuthorizingUser authorizingUser;
    @Deprecated // for workspace apps
    private InstallerUser installerUser;
    @Deprecated // for workspace apps
    private Scopes scopes;

    @Deprecated
    @Data
    public static class AuthorizingUser {
        private String userId;
        private String appHome;
    }

    @Deprecated
    @Data
    public static class InstallerUser {
        private String userId;
        private String appHome;
    }

    @Deprecated
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

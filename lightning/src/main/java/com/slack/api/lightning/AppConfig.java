package com.slack.api.lightning;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.util.http.SlackHttpClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

    public static final class EnvVariableName {
        private EnvVariableName() {
        }

        public static final String SLACK_BOT_TOKEN = "SLACK_BOT_TOKEN";
        public static final String SLACK_SIGNING_SECRET = "SLACK_SIGNING_SECRET";
        public static final String SLACK_VERIFICATION_TOKEN = "SLACK_VERIFICATION_TOKEN";
        public static final String SLACK_APP_CLIENT_ID = "SLACK_APP_CLIENT_ID";
        public static final String SLACK_APP_CLIENT_SECRET = "SLACK_APP_CLIENT_SECRET";
        public static final String SLACK_APP_REDIRECT_URI = "SLACK_APP_REDIRECT_URI";
        public static final String SLACK_APP_SCOPE = "SLACK_APP_SCOPE";
        public static final String SLACK_APP_USER_SCOPE = "SLACK_APP_USER_SCOPE";
        public static final String SLACK_APP_OAUTH_START_PATH = "SLACK_APP_OAUTH_START_PATH";
        public static final String SLACK_APP_OAUTH_CALLBACK_PATH = "SLACK_APP_OAUTH_CALLBACK_PATH";
        public static final String SLACK_APP_OAUTH_CANCELLATION_URL = "SLACK_APP_OAUTH_CANCELLATION_URL";
        public static final String SLACK_APP_OAUTH_COMPLETION_URL = "SLACK_APP_OAUTH_COMPLETION_URL";
    }

    @Builder.Default
    private Slack slack = Slack.getInstance(SlackConfig.DEFAULT, buildSlackHttpClient());

    private static SlackHttpClient buildSlackHttpClient() {
        Map<String, String> userAgentCustomInfo = new HashMap<>();
        String version = AppConfig.class.getPackage().getImplementationVersion();
        userAgentCustomInfo.put("lightning", version == null ? "unknown" : version);
        SlackHttpClient client = new SlackHttpClient(userAgentCustomInfo);
        return client;
    }

    @Builder.Default
    private String singleTeamBotToken = System.getenv(EnvVariableName.SLACK_BOT_TOKEN);
    @Builder.Default
    private String signingSecret = System.getenv(EnvVariableName.SLACK_SIGNING_SECRET);
    @Builder.Default
    @Deprecated
    private String verificationToken = System.getenv(EnvVariableName.SLACK_VERIFICATION_TOKEN);

    // https://api.slack.com/docs/oauth

    public boolean isDistributedApp() {
        return clientId != null && clientSecret != null;
    }

    private boolean oAuthStartEnabled = false;
    private boolean oAuthCallbackEnabled = false;

    // https://api.slack.com/authentication/migration
    private boolean classicAppPermissionsEnabled = false;

    public void setOauthStartPath(String oauthStartPath) {
        this.oauthStartPath = oauthStartPath;
        this.oAuthStartEnabled = oauthStartPath != null;
    }

    public void setOauthCallbackPath(String oauthCallbackPath) {
        this.oauthCallbackPath = oauthCallbackPath;
        this.oAuthCallbackEnabled = oauthCallbackPath != null;
    }

    @Builder.Default
    private String clientId = System.getenv(EnvVariableName.SLACK_APP_CLIENT_ID);
    @Builder.Default
    private String clientSecret = System.getenv(EnvVariableName.SLACK_APP_CLIENT_SECRET);
    @Builder.Default
    private String redirectUri = System.getenv(EnvVariableName.SLACK_APP_REDIRECT_URI);
    @Builder.Default
    private String scope = System.getenv(EnvVariableName.SLACK_APP_SCOPE);
    @Builder.Default
    private String userScope = System.getenv(EnvVariableName.SLACK_APP_USER_SCOPE);

    public String getOauthInstallationUrl(String state) {
        if (clientId == null || scope == null || state == null) {
            return null;
        } else {
            if (isClassicAppPermissionsEnabled()) {
                // https://api.slack.com/authentication/migration
                return "https://slack.com/oauth/authorize" +
                        "?client_id=" + clientId +
                        "&scope=" + scope +
                        "&state=" + state;
            } else {
                return "https://slack.com/oauth/v2/authorize" +
                        "?client_id=" + clientId +
                        "&scope=" + scope +
                        "&user_scope=" + userScope +
                        "&state=" + state;
            }
        }
    }

    private String appPath;

    public String getOauthStartRequestURI() {
        return appPath + oauthStartPath;
    }

    public String getOauthCallbackRequestURI() {
        return appPath + oauthCallbackPath;
    }

    @Builder.Default
    private String oauthStartPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_START_PATH)).orElse("start");
    @Builder.Default
    private String oauthCallbackPath = Optional.ofNullable(System.getenv(EnvVariableName.SLACK_APP_OAUTH_CALLBACK_PATH)).orElse("callback");

    @Builder.Default
    private String oauthCancellationUrl = System.getenv(EnvVariableName.SLACK_APP_OAUTH_CANCELLATION_URL);
    @Builder.Default
    private String oauthCompletionUrl = System.getenv(EnvVariableName.SLACK_APP_OAUTH_COMPLETION_URL);

    private boolean alwaysRequestUserTokenNeeded;

}

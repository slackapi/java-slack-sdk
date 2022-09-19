package com.slack.api.bolt.micronaut;

import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthInstallPageRenderer;
import com.slack.api.bolt.service.builtin.oauth.view.OAuthRedirectUriPageRenderer;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Secondary;

@Secondary
@ConfigurationProperties("slack")
public class SlackConfiguration extends AppConfig {

    // more idiomatic setters e.g. oauth-foo instead of o-auth-foo and bot-token shortcut

    public void setOauthRedirectUriPageRenderer(OAuthRedirectUriPageRenderer oAuthRedirectUriPageRenderer) {
        super.setOAuthRedirectUriPageRenderer(oAuthRedirectUriPageRenderer);
    }

    public void setOauthInstallPageRenderer(OAuthInstallPageRenderer oAuthInstallPageRenderer) {
        super.setOAuthInstallPageRenderer(oAuthInstallPageRenderer);
    }

    public void setOauthInstallPathEnabled(boolean oAuthInstallPathEnabled) {
        super.setOAuthInstallPathEnabled(oAuthInstallPathEnabled);
    }

    public void setOauthRedirectUriPathEnabled(boolean oAuthRedirectUriPathEnabled) {
        super.setOAuthRedirectUriPathEnabled(oAuthRedirectUriPathEnabled);
    }

    public void setOauthInstallPageRenderingEnabled(boolean oAuthInstallPageRenderingEnabled) {
        super.setOAuthInstallPageRenderingEnabled(oAuthInstallPageRenderingEnabled);
    }

    @Deprecated
    public void setOauthCallbackEnabled(boolean enabled) {
        super.setOAuthCallbackEnabled(enabled);
    }

    @Deprecated
    public void setOauthStartEnabled(boolean enabled) {
        super.setOAuthStartEnabled(enabled);
    }

    public void setBotToken(String singleTeamBotToken) {
        super.setSingleTeamBotToken(singleTeamBotToken);
    }
}

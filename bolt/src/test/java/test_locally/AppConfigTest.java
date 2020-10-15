package test_locally;

import com.slack.api.bolt.AppConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppConfigTest {

    @Test
    public void constructor() {
        AppConfig config = new AppConfig();
        assertNotNull(config.getOauthInstallPath());
        assertNotNull(config.getOauthRedirectUriPath());
        assertNotNull(config.getOauthStartPath());
        assertNotNull(config.getOauthCallbackPath());
    }

    @Test
    public void builder() {
        AppConfig config = AppConfig.builder().build();
        assertNotNull(config.getOauthInstallPath());
        assertNotNull(config.getOauthRedirectUriPath());
        assertNotNull(config.getOauthStartPath());
        assertNotNull(config.getOauthCallbackPath());
    }

    @Test
    public void accessors() {
        AppConfig config = new AppConfig();
        config.setOauthInstallPath("/custom-start");
        config.setOauthRedirectUriPath("/custom-callback");
        assertEquals("/custom-start", config.getOauthInstallRequestURI());
        assertEquals("/custom-callback", config.getOauthRedirectUriRequestURI());
        assertEquals("/custom-start", config.getOauthStartRequestURI());
        assertEquals("/custom-callback", config.getOauthCallbackRequestURI());
    }

    @Test
    public void legacy_accessors() {
        AppConfig config = new AppConfig();
        config.setOauthStartPath("/custom-start");
        config.setOauthCallbackPath("/custom-callback");
        assertEquals("/custom-start", config.getOauthInstallRequestURI());
        assertEquals("/custom-callback", config.getOauthRedirectUriRequestURI());
        assertEquals("/custom-start", config.getOauthStartRequestURI());
        assertEquals("/custom-callback", config.getOauthCallbackRequestURI());
    }

    @Test
    public void accessors_appPath() {
        AppConfig config = new AppConfig();
        config.setAppPath("/app");
        config.setOauthInstallPath("/custom-start");
        config.setOauthRedirectUriPath("/custom-callback");
        assertEquals("/app/custom-start", config.getOauthInstallRequestURI());
        assertEquals("/app/custom-callback", config.getOauthRedirectUriRequestURI());
        assertEquals("/app/custom-start", config.getOauthStartRequestURI());
        assertEquals("/app/custom-callback", config.getOauthCallbackRequestURI());
    }

    @Test
    public void legacy_accessors_appPath() {
        AppConfig config = new AppConfig();
        config.setAppPath("/app");
        config.setOauthStartPath("/start");
        config.setOauthCallbackPath("/callback");
        assertEquals("/app/start", config.getOauthStartRequestURI());
        assertEquals("/app/callback", config.getOauthCallbackRequestURI());
    }

    @Test
    public void builders() {
        AppConfig config = AppConfig.builder()
                .oauthInstallPath("/slack/install")
                .oauthRedirectUriPath("/slack/oauth_redirect")
                .build();
        assertEquals("/slack/install", config.getOauthInstallPath());
        assertEquals("/slack/oauth_redirect", config.getOauthRedirectUriPath());

        assertEquals("/slack/install", config.getOauthStartPath());
        assertEquals("/slack/oauth_redirect", config.getOauthCallbackPath());
    }

    @Test
    public void legacy_builders() {
        AppConfig config = AppConfig.builder()
                .oauthStartPath("/slack/install")
                .oauthCallbackPath("/slack/oauth_redirect")
                .build();
        assertEquals("/slack/install", config.getOauthInstallPath());
        assertEquals("/slack/oauth_redirect", config.getOauthRedirectUriPath());

        assertEquals("/slack/install", config.getOauthStartPath());
        assertEquals("/slack/oauth_redirect", config.getOauthCallbackPath());
    }

}

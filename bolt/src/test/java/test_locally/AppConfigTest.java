package test_locally;

import com.slack.api.bolt.AppConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppConfigTest {

    @Test
    public void constructor() {
        AppConfig config = new AppConfig();
        assertNotNull(config.getOauthCallbackPath());
    }

    @Test
    public void builder() {
        AppConfig config = AppConfig.builder().build();
        assertNotNull(config.getOauthCallbackPath());
    }

    @Test
    public void accessors() {
        AppConfig config = new AppConfig();
        config.setOauthStartPath("/start");
        config.setOauthCallbackPath("/callback");
        assertEquals("/start", config.getOauthStartRequestURI());
        assertEquals("/callback", config.getOauthCallbackRequestURI());
    }

    @Test
    public void accessors_appPath() {
        AppConfig config = new AppConfig();
        config.setAppPath("/app");
        config.setOauthStartPath("/start");
        config.setOauthCallbackPath("/callback");
        assertEquals("/app/start", config.getOauthStartRequestURI());
        assertEquals("/app/callback", config.getOauthCallbackRequestURI());
    }

}

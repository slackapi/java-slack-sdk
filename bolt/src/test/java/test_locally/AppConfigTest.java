package test_locally;

import com.slack.api.bolt.AppConfig;
import org.junit.Test;

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

}

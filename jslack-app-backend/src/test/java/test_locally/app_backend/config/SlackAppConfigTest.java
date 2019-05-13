package test_locally.app_backend.config;

import com.github.seratch.jslack.app_backend.config.SlackAppConfig;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class SlackAppConfigTest {

    @Test
    public void instantiation() {
        SlackAppConfig config = new SlackAppConfig();
        config.setClientId("a");
        config.setClientSecret("b");
        config.setRedirectUri("https://example.com/");

        assertThat(config, is(notNullValue()));
        assertThat(config.getClientId(), is("a"));
        assertThat(config.getClientSecret(), is("b"));
        assertThat(config.getRedirectUri(), is("https://example.com/"));
    }

    @Test
    public void builder() {
        SlackAppConfig config = SlackAppConfig.builder()
                .clientId("a")
                .clientSecret("b")
                .redirectUri("https://example.com/")
                .build();

        assertThat(config, is(notNullValue()));
        assertThat(config.getClientId(), is("a"));
        assertThat(config.getClientSecret(), is("b"));
        assertThat(config.getRedirectUri(), is("https://example.com/"));
    }
}
package test_locally;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class AppTest {

    @Test
    public void getOauthInstallationUrl_v1() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .classicAppPermissionsEnabled(true)
                .build();
        App app = new App(config);
        String url = app.getOauthInstallationUrl("state-value");
        assertEquals("https://slack.com/oauth/authorize?client_id=123&scope=commands,chat:write&state=state-value", url);
    }

    @Test
    public void getOauthInstallationUrl_v2() {
        AppConfig config = AppConfig.builder()
                .signingSecret("secret")
                .clientId("123")
                .scope("commands,chat:write")
                .userScope("search:read")
                .build();
        App app = new App(config);
        String url = app.getOauthInstallationUrl("state-value");
        assertEquals("https://slack.com/oauth/v2/authorize?client_id=123&scope=commands,chat:write&user_scope=search:read&state=state-value", url);
    }

    @Test
    public void getOauthInstallationUrl_null() {
        App app = new App();
        String url = app.getOauthInstallationUrl("state-value");
        assertNull(url);
    }

    @Test
    public void status() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        assertThat(app.status(), is(App.Status.Stopped));
        app.start();
        assertThat(app.status(), is(App.Status.Running));
        app.stop();
        assertThat(app.status(), is(App.Status.Stopped));
        app.start();
        assertThat(app.status(), is(App.Status.Running));
        app.stop();
        assertThat(app.status(), is(App.Status.Stopped));
        app.start();
        app.start();
        assertThat(app.status(), is(App.Status.Running));
    }

    @Test
    public void builder_status() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        assertNotNull(app.status());
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toBuilder().build();
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toOAuthCallbackApp();
        assertThat(app.status(), is(App.Status.Stopped));

        app = app.toOAuthStartApp();
        assertThat(app.status(), is(App.Status.Stopped));
    }

    @Test
    public void builder_config() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        assertNotNull(app.config());

        app = app.toBuilder().build();
        assertNotNull(app.config());

        app = app.toOAuthCallbackApp();
        assertNotNull(app.config());

        app = app.toOAuthStartApp();
        assertNotNull(app.config());
    }

    @Test
    public void initializer_called() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.initialize();
        assertThat(called.get(), is(true));
    }

    @Test
    public void initializer_start() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.start();
        assertThat(called.get(), is(true));
    }

    @Test
    public void initializer_same_key() {
        App app = new App(AppConfig.builder().signingSecret("secret").build());
        final AtomicBoolean called = new AtomicBoolean(false);
        assertThat(called.get(), is(false));

        app.initializer("foo", (theApp) -> {
            called.set(true);
        });
        app.initializer("foo", (theApp) -> {
        });
        app.initialize();
        assertThat(called.get(), is(false));
    }

}

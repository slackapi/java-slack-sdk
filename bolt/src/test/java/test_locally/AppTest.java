package test_locally;

import com.slack.api.bolt.App;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class AppTest {

    @Test
    public void status() {
        App app = new App();
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
        App app = new App();
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
        App app = new App();
        assertNotNull(app.config());

        app = app.toBuilder().build();
        assertNotNull(app.config());

        app = app.toOAuthCallbackApp();
        assertNotNull(app.config());

        app = app.toOAuthStartApp();
        assertNotNull(app.config());
    }

}

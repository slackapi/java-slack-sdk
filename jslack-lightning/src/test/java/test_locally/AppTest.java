package test_locally;

import com.github.seratch.jslack.lightning.App;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}

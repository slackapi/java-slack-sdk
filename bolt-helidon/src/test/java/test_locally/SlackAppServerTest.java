package test_locally;

import com.slack.api.bolt.App;
import com.slack.api.bolt.helidon.SlackAppServer;
import io.helidon.config.Config;
import io.helidon.metrics.MetricsSupport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackAppServerTest {

    @Test
    public void constructors() {
        new SlackAppServer(new App());
        new SlackAppServer(Config.create(), new App());
        new SlackAppServer(new App(), new App());
        new SlackAppServer(Config.create(), new App(), new App());
    }

    @Test
    public void testAdditionalRoutingConfigurator() {
        SlackAppServer server = new SlackAppServer(new App());
        MetricsSupport metrics = MetricsSupport.create();
        server.setAdditionalRoutingConfigurator((builder -> builder.register(metrics)));
        assertNotNull(server.getAdditionalRoutingConfigurator());
    }

    @Test
    public void testShutdownTimeoutSeconds() {
        SlackAppServer server = new SlackAppServer(new App());
        assertEquals(10, server.getShutdownTimeoutSeconds());
        server.setShutdownTimeoutSeconds(20);
        assertEquals(20, server.getShutdownTimeoutSeconds());
    }
}

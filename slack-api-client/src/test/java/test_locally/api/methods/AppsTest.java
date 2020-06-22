package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static test_locally.api.status.ApiTest.ValidToken;

public class AppsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void apiTest() throws Exception {
        assertThat(slack.methods(ValidToken).appsUninstall(r -> r.clientId("x").clientSecret("y"))
                .isOk(), is(true));
    }

    @Test
    public void apiTest_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).appsUninstall(r -> r.clientId("x").clientSecret("y"))
                .get().isOk(), is(true));
    }

}

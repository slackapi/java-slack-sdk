package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.response.api.ApiTestResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiTest {

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
        ApiTestResponse response = slack.methods().apiTest(r -> r.error("error").foo("bar"));
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is(""));
    }

    @Test
    public void apiTest_async() throws Exception {
        ApiTestResponse response = slack.methodsAsync().apiTest(r -> r.error("error").foo("bar")).get();
        assertThat(response.isOk(), is(true));
        assertThat(response.getArgs().getFoo(), is(""));
    }

}

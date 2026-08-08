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

public class BlocksTest {

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
    public void validate() throws Exception {
        assertThat(slack.methods(ValidToken).blocksValidate(r -> r
                .blocks("[{\"type\":\"section\",\"text\":{\"type\":\"plain_text\",\"text\":\"Hello world\"}}]")).isOk(), is(true));
    }

    @Test
    public void validate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).blocksValidate(r -> r
                .blocks("[{\"type\":\"section\",\"text\":{\"type\":\"plain_text\",\"text\":\"Hello world\"}}]")).get().isOk(), is(true));
    }
}

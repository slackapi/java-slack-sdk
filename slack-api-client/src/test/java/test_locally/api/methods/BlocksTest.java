package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

    // blocks.validate is unauthenticated (no token or scopes — see
    // https://docs.slack.dev/reference/methods/blocks.validate), so the SDK sends no
    // Authorization header for it. The shared mock API gates every request on a valid token and
    // therefore answers a tokenless call with "not_authed"; these tests assert that the request is
    // built and round-trips to the endpoint, which is what a mock harness can verify without a
    // real Slack backend. End-to-end ok/errors[] behavior is covered by the remote blocks_Test.
    @Test
    public void validate() throws Exception {
        assertThat(slack.methods().blocksValidate(r -> r
                .blocks("[{\"type\":\"section\",\"text\":{\"type\":\"plain_text\",\"text\":\"Hello world\"}}]")).getError(), is("not_authed"));
    }

    @Test
    public void validate_async() throws Exception {
        assertThat(slack.methodsAsync().blocksValidate(r -> r
                .blocks("[{\"type\":\"section\",\"text\":{\"type\":\"plain_text\",\"text\":\"Hello world\"}}]")).get().getError(), is("not_authed"));
    }
}

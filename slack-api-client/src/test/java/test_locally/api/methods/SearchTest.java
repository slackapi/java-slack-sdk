package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class SearchTest {

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
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).searchAll(r -> r.query("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).searchFiles(r -> r.query("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).searchMessages(r -> r.query("foo"))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).searchAll(r -> r.query("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).searchFiles(r -> r.query("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).searchMessages(r -> r.query("foo"))
                .get().isOk(), is(true));
    }

}

package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class MpimTest {

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
        assertThat(slack.methods(ValidToken).mpimClose(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).mpimHistory(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).mpimList(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).mpimMark(r -> r.channel("C123").ts("123.123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).mpimOpen(r -> r.users(Arrays.asList("U123", "U234")))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).mpimReplies(r -> r.channel("C123"))
                .isOk(), is(true));
    }

}

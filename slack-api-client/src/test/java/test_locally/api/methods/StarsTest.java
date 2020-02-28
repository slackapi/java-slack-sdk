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

public class StarsTest {

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
        assertThat(slack.methods(ValidToken).starsAdd(r -> r.channel("C123").timestamp("123.123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).starsList(r -> r.page(1).count(1))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).starsRemove(r -> r.channel("C123").timestamp("123.123"))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).starsAdd(r -> r.channel("C123").timestamp("123.123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).starsList(r -> r.page(1).count(1))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).starsRemove(r -> r.channel("C123").timestamp("123.123"))
                .get().isOk(), is(true));
    }

}

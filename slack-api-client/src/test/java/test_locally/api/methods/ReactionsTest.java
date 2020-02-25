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

public class ReactionsTest {

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
        assertThat(slack.methods(ValidToken).reactionsAdd(r -> r.channel("C123").timestamp("123.123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).reactionsGet(r -> r.channel("C123").timestamp("123.123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).reactionsList(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).reactionsRemove(r -> r.channel("C123").name("smile").timestamp("123.123"))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).reactionsAdd(r -> r.channel("C123").timestamp("123.123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).reactionsGet(r -> r.channel("C123").timestamp("123.123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).reactionsList(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).reactionsRemove(r -> r.channel("C123").name("smile").timestamp("123.123"))
                .get().isOk(), is(true));
    }

}

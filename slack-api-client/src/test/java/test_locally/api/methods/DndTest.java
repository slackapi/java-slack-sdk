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

public class DndTest {

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
        assertThat(slack.methods(ValidToken).dndEndDnd(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndEndSnooze(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndInfo(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndSetSnooze(r -> r.numMinutes(10))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).dndTeamInfo(r -> r.users(Arrays.asList("U123")))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).dndEndDnd(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndEndSnooze(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndInfo(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndSetSnooze(r -> r.numMinutes(10))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).dndTeamInfo(r -> r.users(Arrays.asList("U123")))
                .get().isOk(), is(true));
    }

}

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

public class RemindersTest {

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
        assertThat(slack.methodsAsync(ValidToken).remindersAdd(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).remindersComplete(r -> r.reminder("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).remindersComplete(r -> r.reminder("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).remindersDelete(r -> r.reminder("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).remindersInfo(r -> r.reminder("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync().remindersList(r -> r.token(ValidToken))
                .get().isOk(), is(true));
    }

}

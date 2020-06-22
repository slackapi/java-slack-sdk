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

public class CallsTest {

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
    public void calls() throws Exception {
        assertThat(slack.methods(ValidToken).callsAdd(r -> r.title("test call")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).callsEnd(r -> r.id("R111")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).callsInfo(r -> r.id("R111")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).callsUpdate(r -> r.id("R111")).isOk(), is(true));
        assertThat(slack.methods(ValidToken).callsParticipantsAdd(r -> r.id("R111")).isOk(), is(true));
    }

    @Test
    public void calls_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).callsAdd(r -> r.title("test call")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).callsEnd(r -> r.id("R111")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).callsInfo(r -> r.id("R111")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).callsUpdate(r -> r.id("R111")).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).callsParticipantsAdd(r -> r.id("R111")).get().isOk(), is(true));
    }

}

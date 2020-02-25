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

public class ChatTest {

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
    public void postMessage() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatPostMessage(r -> r.channel("C123").text("Hi!")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatPostEphemeral() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatPostEphemeral(r -> r.channel("C123").text("Hi!")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatDelete() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatDelete(r -> r.channel("C123").ts("123.123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatDeleteScheduledMessage() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatDeleteScheduledMessage(r ->
                        r.channel("C123").scheduledMessageId("id")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatGetPermalink() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatGetPermalink(r ->
                        r.channel("C123").messageTs("123.123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatMeMessage() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatMeMessage(r ->
                        r.channel("C123").text("Hi")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatScheduleMessage() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduleMessage(r ->
                        r.channel("C123").text("Hi").postAt(123)
                ).get().isOk(), is(true));
    }

    @Test
    public void chatUnfurl() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatUnfurl(r ->
                        r.channel("C123").ts("123.123")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatUpdate() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatUpdate(r ->
                        r.channel("C123").ts("123.123").text("(edited)")
                ).get().isOk(), is(true));
    }

    @Test
    public void chatScheduledMessagesList() throws Exception {
        assertThat(
                slack.methodsAsync(ValidToken).chatScheduledMessagesList(r -> r.channel("C123"))
                        .get().isOk(), is(true));
    }

}

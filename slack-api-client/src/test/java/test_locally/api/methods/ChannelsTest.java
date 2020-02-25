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

public class ChannelsTest {

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
        assertThat(slack.methods(ValidToken).channelsArchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsCreate(r -> r.name("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsHistory(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsInfo(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsInvite(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsJoin(r -> r.name("general"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsKick(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsLeave(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsList(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsRename(r -> r.channel("C123").name("new name"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsReplies(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsSetPurpose(r -> r.channel("C123").purpose("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsSetTopic(r -> r.channel("C123").topic("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).channelsUnarchive(r -> r.channel("C123"))
                .isOk(), is(true));
    }

}

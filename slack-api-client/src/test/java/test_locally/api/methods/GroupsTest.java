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

public class GroupsTest {

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
        assertThat(slack.methods(ValidToken).groupsArchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsCreate(r -> r.name("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsCreateChild(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsHistory(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsInfo(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsInvite(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsKick(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsLeave(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsList(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsMark(r -> r.channel("C123").ts("123.123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsOpen(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsRename(r -> r.channel("C123").name("new name"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsReplies(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsSetPurpose(r -> r.channel("C123").purpose("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsSetTopic(r -> r.channel("C123").topic("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).groupsUnarchive(r -> r.channel("C123"))
                .isOk(), is(true));
    }

}

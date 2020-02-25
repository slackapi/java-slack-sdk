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

public class ConversationsTest {

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
        assertThat(slack.methodsAsync(ValidToken).conversationsArchive(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsClose(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsCreate(r -> r.name("foo"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsHistory(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsInfo(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsInvite(r -> r.channel("C123").users(Arrays.asList("U123")))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsJoin(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsKick(r -> r.channel("C123").user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsLeave(r -> r.channel("C123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsList(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsMembers(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsOpen(r -> r.users(Arrays.asList("U123")).channel("D123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsRename(r -> r.channel("C123").name("new name"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsReplies(r -> r.channel("C123").limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsSetPurpose(r -> r.channel("C123").purpose("something"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsSetTopic(r -> r.channel("C123").topic("something"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsUnarchive(r -> r.channel("C123"))
                .get().isOk(), is(true));
    }

}

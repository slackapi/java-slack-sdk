package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.model.ConversationType;
import com.slack.api.scim.metrics.MemoryMetricsDatastore;
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
    Slack slack = null;

    @Before
    public void setup() throws Exception {
        server.start();
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        config.setMethodsConfig(new MethodsConfig());
        // skip the burst traffic detection for these tests
        config.getMethodsConfig().setStatsEnabled(false);
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).conversationsArchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsClose(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsCreate(r -> r.name("foo"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsHistory(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInfo(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInvite(r -> r.channel("C123").users(Arrays.asList("U123")))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsJoin(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsKick(r -> r.channel("C123").user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsLeave(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsList(r -> r
                .limit(1).cursor("xxx").types(Arrays.asList(ConversationType.PUBLIC_CHANNEL)))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsMark(r -> r.channel("C123").ts("111.222"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsMembers(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsOpen(r -> r.users(Arrays.asList("U123")).channel("D123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsRename(r -> r.channel("C123").name("new name"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsReplies(r -> r.channel("C123").limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsSetPurpose(r -> r.channel("C123").purpose("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsSetTopic(r -> r.channel("C123").topic("something"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsUnarchive(r -> r.channel("C123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsInviteShared(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsAcceptSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsApproveSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsDeclineSharedInvite(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).conversationsListConnectInvites(r -> r)
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
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
        assertThat(slack.methodsAsync(ValidToken).conversationsList(r -> r
                .limit(1).cursor("xxx").types(Arrays.asList(ConversationType.PUBLIC_CHANNEL)))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsMark(r -> r.channel("C123").ts("111.222"))
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
        assertThat(slack.methodsAsync(ValidToken).conversationsInviteShared(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsAcceptSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsApproveSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsDeclineSharedInvite(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).conversationsListConnectInvites(r -> r)
                .get().isOk(), is(true));
    }

}

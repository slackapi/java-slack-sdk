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

public class FilesRemoteTest {

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
        assertThat(slack.methods(ValidToken).filesRemoteAdd(r -> r.externalId("external id").title("title"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRemoteInfo(r -> r.externalId("external id"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRemoteList(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRemoteRemove(r -> r.externalId("external id"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRemoteShare(r ->
                r.externalId("external id").channels(Arrays.asList("C123")))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).filesRemoteUpdate(r -> r.externalId("external id"))
                .isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).filesRemoteAdd(r -> r.externalId("external id").title("title"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRemoteInfo(r -> r.externalId("external id"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRemoteList(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRemoteRemove(r -> r.externalId("external id"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRemoteShare(r ->
                r.externalId("external id").channels(Arrays.asList("C123")))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRemoteUpdate(r -> r.externalId("external id"))
                .get().isOk(), is(true));
    }

}

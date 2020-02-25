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

public class FilesTest {

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
        assertThat(slack.methodsAsync(ValidToken).filesDelete(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesInfo(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesList(r -> r.channel("C123").count(1).page(10))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesRevokePublicURL(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesSharedPublicURL(r -> r.file("F123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).filesUpload(r -> r.filename("name").title("title"))
                .get().isOk(), is(true));
    }

}

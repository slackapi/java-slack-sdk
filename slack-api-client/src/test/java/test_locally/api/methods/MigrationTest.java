package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class MigrationTest {

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

    // TODO
    @Ignore
    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).migrationExchange(r -> r.users(Arrays.asList("U123")))
                .isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).migrationExchange(r -> r.users(Arrays.asList("U123")))
                .get().isOk(), is(true));
    }

}

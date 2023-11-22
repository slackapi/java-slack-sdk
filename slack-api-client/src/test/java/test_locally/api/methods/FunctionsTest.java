package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidFunctionToken;

public class FunctionsTest {

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
        assertThat(slack.methodsAsync(ValidFunctionToken).functionsCompleteSuccess(r -> r
                .functionExecutionId("Fn11111111")
                .outputs(new HashMap<>())
        ).get().getError(), is(""));

        assertThat(slack.methodsAsync(ValidFunctionToken).functionsCompleteError(r -> r
                .functionExecutionId("Fn11111111")
                .error("something wrong")
        ).get().getError(), is(""));
    }
}

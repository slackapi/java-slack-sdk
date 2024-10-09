package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsCustomRateLimitResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static util.MockSlackApi.ValidToken;

public class CustomRateLimitsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    Slack slack;

    static int customLimit = 30;

    static class MyCustomRateLimitResolver implements MethodsCustomRateLimitResolver {

        @Override
        public Optional<Integer> getCustomAllowedRequestsPerMinute(String teamId, String methodName) {
            if (methodName.equals("auth.test")) {
                return Optional.of(customLimit);
            }
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getCustomAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel) {
            return Optional.empty();
        }

        @Override
        public Optional<Integer> getCustomAllowedRequestsForAssistantThreadsSetStatusPerMinute(String teamId, String channel) {
            return Optional.empty();
        }
    }

    @Before
    public void setup() throws Exception {
        server.start();
        SlackConfig config = new SlackConfig();
        // Customize the rate limits
        config.getMethodsConfig().setCustomRateLimitResolver(new MyCustomRateLimitResolver());
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void burstTest() throws Exception {
        AsyncMethodsClient client = slack.methodsAsync(ValidToken);
        client.authTest(r -> r);
        client.authTest(r -> r);
        client.authTest(r -> r);
        client.authTest(r -> r);
        client.authTest(r -> r);

        long startMillis = System.currentTimeMillis();
        // With the default settings, this call is on the "safe" pace, so spentMillis will be 15L or so
        client.authTest(r -> r).get();
        long spentMillis = System.currentTimeMillis() - startMillis;
        assertThat(spentMillis, is(greaterThan(500L )));
    }

}

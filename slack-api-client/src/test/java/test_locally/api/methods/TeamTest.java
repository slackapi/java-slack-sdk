package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.MethodsCustomRateLimitResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class TeamTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        MethodsConfig methodsConfig = new MethodsConfig();
        methodsConfig.setCustomRateLimitResolver(new MethodsCustomRateLimitResolver() {
            @Override
            public Optional<Integer> getCustomAllowedRequestsPerMinute(String teamId, String methodName) {
                return Optional.of(20);
            }

            @Override
            public Optional<Integer> getCustomAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel) {
                return Optional.empty();
            }
        });
        config.setMethodsConfig(methodsConfig);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).teamAccessLogs(r -> r.count(1).page(1))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamBillableInfo(r -> r.user("U123"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamInfo(r -> r)
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamIntegrationLogs(r -> r.user("U123").appId("12345"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamProfileGet(r -> r.visibility("v"))
                .isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamBillingInfo(r -> r).isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamPreferencesList(r -> r).isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamExternalTeamsList(r -> r).isOk(), is(true));
        assertThat(slack.methods(ValidToken).teamExternalTeamsDisconnect(r -> r).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).teamAccessLogs(r -> r.count(1).page(1))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamBillableInfo(r -> r.user("U123"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamInfo(r -> r)
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamIntegrationLogs(r -> r.user("U123").appId("12345"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamProfileGet(r -> r.visibility("v"))
                .get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamBillingInfo(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamPreferencesList(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamExternalTeamsList(r -> r).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).teamExternalTeamsDisconnect(r -> r).get().isOk(), is(true));
    }

}

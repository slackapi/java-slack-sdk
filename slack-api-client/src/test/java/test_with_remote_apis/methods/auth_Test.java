package test_with_remote_apis.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthRevokeResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Slf4j
public class auth_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void authRevoke() throws IOException, SlackApiException {
        AuthRevokeResponse response = slack.methods().authRevoke(req -> req.token("dummy").test(true));
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("invalid_auth"));
        assertThat(response.isRevoked(), is(false));
    }

    @Test
    public void authTest_user() throws IOException, SlackApiException {
        AuthTestResponse response = slack.methods().authTest(req -> req.token(userToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_user_async() throws Exception {
        AuthTestResponse response = slack.methodsAsync().authTest(req -> req.token(userToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_user_2() throws IOException, SlackApiException {
        AuthTestResponse response = slack.methods(userToken).authTest(req -> req);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_user_2_async() throws Exception {
        AuthTestResponse response = slack.methodsAsync(userToken).authTest(req -> req).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_bot() throws IOException, SlackApiException {
        AuthTestResponse response = slack.methods(botToken).authTest(req -> req);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_bot_async() throws Exception {
        AuthTestResponse response = slack.methodsAsync(botToken).authTest(req -> req).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
    }

    @Test
    public void authTest_grid() throws IOException, SlackApiException {
        String gridAdminToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
        AuthTestResponse response = slack.methods().authTest(req -> req.token(gridAdminToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
        assertThat(response.getEnterpriseId(), is(notNullValue()));
    }

    @Test
    public void authTest_grid_async() throws Exception {
        String gridAdminToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
        AuthTestResponse response = slack.methodsAsync().authTest(req -> req.token(gridAdminToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getUrl(), is(notNullValue()));
        assertThat(response.getEnterpriseId(), is(notNullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public void authTest_missingToken() throws IOException, SlackApiException {
        SlackConfig config = new SlackConfig();
        config.setTokenExistenceVerificationEnabled(true);
        Slack slack = Slack.getInstance(config);
        slack.methods().authTest(req -> req);
    }

    @Ignore
    @Test
    public void authTest_burst() throws Exception {
        AuthTestResponse authTest = slack.methodsAsync(botToken).authTest(r -> r).get();
        List<CompletableFuture<AuthTestResponse>> futures = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            futures.add(slack.methodsAsync(botToken).authTest(r -> r));
        }

        boolean currentQueueSizeWorking = false;
        for (int i = 0; i < 300; i++) {
            if (currentQueueSizeWorking) {
                break;
            }
            MethodsStats stats = testConfig.getMetricsDatastore().getStats(authTest.getTeamId());
            currentQueueSizeWorking = stats.getCurrentQueueSize().get(Methods.AUTH_TEST) > 0;
            Thread.sleep(10L);
        }

        // block here for completing all the tasks
        for (CompletableFuture<AuthTestResponse> future : futures) {
            future.get();
        }

        MethodsStats stats = testConfig.getMetricsDatastore().getStats(authTest.getTeamId());
        log.info("stats: {}", GsonFactory.createCamelCase(testConfig.getConfig()).toJson(stats));
        assertThat(stats.getRateLimitedMethods().containsKey("auth.test"), is(false));
    }

    @Ignore
    @Test
    public void lastMinuteRequests() throws Exception {
        String teamId = slack.methods().authTest(r -> r.token(botToken)).getTeamId();
        MethodsStats before = testConfig.getMetricsDatastore().getStats(teamId);

        AuthTestResponse sync = slack.methods().authTest(r -> r.token(botToken));
        assertThat(sync.getError(), is(nullValue()));
        AuthTestResponse async = slack.methodsAsync().authTest(r -> r.token(botToken)).get();
        assertThat(async.getError(), is(nullValue()));

        MethodsStats after = testConfig.getMetricsDatastore().getStats(teamId);
        Integer beforeCount = before.getLastMinuteRequests().get(Methods.AUTH_TEST);
        if (beforeCount == null) {
            beforeCount = 0;
        }
        Integer afterCount = after.getLastMinuteRequests().get(Methods.AUTH_TEST);
        assertThat(afterCount - beforeCount, is(2));

        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());
        for (int i = 0; i < 6; i++) {
            MethodsStats stats = testConfig.getMetricsDatastore().getStats(teamId);
            log.debug("Waiting for the last minute requests update... {}", gson.toJson(stats.getLastMinuteRequests()));
            Thread.sleep(10000);
        }
        Thread.sleep(3000);

        MethodsStats oneMinuteLater = testConfig.getMetricsDatastore().getStats(teamId);
        Integer oneMinuteLaterCount = oneMinuteLater.getLastMinuteRequests().get(Methods.AUTH_TEST);
        log.debug("Final stats: {}", gson.toJson(oneMinuteLater.getLastMinuteRequests()));
        assertThat(afterCount - oneMinuteLaterCount, is(greaterThanOrEqualTo(2)));
    }

}

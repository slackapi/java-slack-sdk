package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static util.MockSlackApi.RateLimitedToken;

@Slf4j
public class RateLimitedTest {

    MockSlackApiServer server = new MockSlackApiServer();
    String executorName;
    SlackConfig config;
    Slack slack;

    @Before
    public void setup() throws Exception {
        server.start();
        executorName = RateLimitedTest.class.getCanonicalName() + "_" + System.currentTimeMillis();
        config = new SlackConfig();
        config.getMethodsConfig().setExecutorName(executorName);
        config.synchronizeMetricsDatabases();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());

        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void sync() throws Exception {
        MethodsClient client = slack.methods(RateLimitedToken);
        try {
            client.usersList(r -> r);
        } catch (SlackApiException e) {
            assertThat(e.getResponse().code(), is(429));
            assertThat(e.getResponse().header("Retry-After"), is("3"));
            MetricsDatastore datastore = config.getMethodsConfig().getMetricsDatastore();
            log.debug("stats: {}", datastore.getAllStats());

            Integer numOfRequests = datastore.getNumberOfLastMinuteRequests(
                    executorName,
                    "T1234567",
                    Methods.USERS_LIST);
            assertThat(numOfRequests, is(1));

            Long millisToResume = datastore.getRateLimitedMethodRetryEpochMillis(
                    executorName,
                    "T1234567",
                    Methods.USERS_LIST);
            assertThat(millisToResume, is(greaterThan(0L)));
        }
    }

    @Test
    public void async() throws Exception {
        try {
            slack.methodsAsync(RateLimitedToken).usersList(r -> r).get(2, TimeUnit.SECONDS);
        } catch (Exception ee) {
            MetricsDatastore datastore = config.getMethodsConfig().getMetricsDatastore();
            log.debug("stats: {}", datastore.getAllStats());

            Integer numOfRequests = datastore.getNumberOfLastMinuteRequests(
                    executorName,
                    "T1234567",
                    Methods.USERS_LIST);
            assertThat(numOfRequests, is(1));

            Long millisToResume = datastore.getRateLimitedMethodRetryEpochMillis(
                    executorName,
                    "T1234567",
                    Methods.USERS_LIST);
            assertThat(millisToResume, is(greaterThan(0L)));
        }
    }

    @Test
    public void chatPostMessageSync() throws Exception {
        MethodsClient client = slack.methods(RateLimitedToken);
        try {
            client.chatPostMessage(r -> r.text("Hey!").channel("C12345"));
        } catch (SlackApiException e) {
            assertThat(e.getResponse().code(), is(429));
            assertThat(e.getResponse().header("Retry-After"), is("3"));
            MetricsDatastore datastore = config.getMethodsConfig().getMetricsDatastore();
            log.debug("stats: {}", datastore.getAllStats());

            Integer numOfRequests = datastore.getNumberOfLastMinuteRequests(
                    executorName,
                    "T1234567",
                    Methods.CHAT_POST_MESSAGE + "_C12345");
            assertThat(numOfRequests, is(1));

            Long millisToResume = datastore.getRateLimitedMethodRetryEpochMillis(
                    executorName,
                    "T1234567",
                    Methods.CHAT_POST_MESSAGE + "_C12345");
            assertThat(millisToResume, is(greaterThan(0L)));
        }
    }

    @Test
    public void chatPostMessageAsync() throws Exception {
        try {
            slack.methodsAsync(RateLimitedToken)
                    .chatPostMessage(r -> r.text("Hey!").channel("C12345"))
                    .get(2, TimeUnit.SECONDS);
        } catch (Exception ee) {
            MetricsDatastore datastore = config.getMethodsConfig().getMetricsDatastore();
            log.debug("stats: {}", datastore.getAllStats());

            Integer numOfRequests = datastore.getNumberOfLastMinuteRequests(
                    executorName,
                    "T1234567",
                    Methods.CHAT_POST_MESSAGE + "_C12345");
            assertThat(numOfRequests, is(1));

            Long millisToResume = datastore.getRateLimitedMethodRetryEpochMillis(
                    executorName,
                    "T1234567",
                    Methods.CHAT_POST_MESSAGE + "_C12345");
            assertThat(millisToResume, is(greaterThan(0L)));
        }
    }
}

package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class RateLimiterTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @Test
    public void sequentialRequests() throws Exception {
        assertThat(botToken, is(notNullValue()));
        for (int i = 0; i < 3; i++) {
            CompletableFuture<ConversationsListResponse> response = slack.methodsAsync(botToken).conversationsList(r -> r.limit(1));
            response.get();
        }

        String teamId = slack.methods().teamInfo(r -> r.token(botToken)).getTeam().getId();
        MethodsStats stats = testConfig.getMetricsDatastore().getStats(teamId);
        assertThat(stats.getSuccessfulCalls().get(Methods.CONVERSATIONS_LIST), is(greaterThanOrEqualTo(3L)));
        assertThat(stats.getLastMinuteRequests().get(Methods.CONVERSATIONS_LIST), is(greaterThanOrEqualTo(3)));
    }

    @Test
    public void controlRequestsAndCleanupData() throws Exception {
        assertThat(botToken, is(notNullValue()));

        String teamId = slack.methods().teamInfo(r -> r.token(botToken)).getTeam().getId();
        MethodsStats stats = testConfig.getMetricsDatastore().getStats(teamId);
        Integer beforeRequestsPerMinute = stats.getLastMinuteRequests().get(Methods.CONVERSATIONS_LIST);
        if (beforeRequestsPerMinute == null) {
            beforeRequestsPerMinute = 0;
        }

        long start = System.currentTimeMillis();
        long totalCalls = 0L;
        totalCalls = runBatch(totalCalls, 1L, 0L); // 0 second
        totalCalls = runBatch(totalCalls, 4L, 2000L); // 8 seconds
        totalCalls = runBatch(totalCalls, 5L, 1000L); // 5 seconds
        totalCalls = runBatch(totalCalls, 10L, 300L); // 3 seconds
        totalCalls = runBatch(totalCalls, 10L, 50L); // 0.5 seconds

        assertThat(totalCalls, is(30L)); // 30 calls

        long end = System.currentTimeMillis();
        long spentMillis = end - start;

        // 10 + 8 + 10 + 5 + 10 + 3 + 7 = 53
        // (60 seconds / 20) * 30 = 90
        log.info("spent: {} milliseconds", spentMillis);

        stats = testConfig.getMetricsDatastore().getStats(teamId);
        Integer currentSize = stats.getCurrentQueueSize().get(Methods.CONVERSATIONS_LIST);
        log.info("current queue size: {}", currentSize);

        // Make sure if the cleanup background job works
        Thread.sleep(5000L);
        stats = testConfig.getMetricsDatastore().getStats(teamId);
        Integer requestsPerMinute = stats.getLastMinuteRequests().get(Methods.CONVERSATIONS_LIST);
        assertThat(requestsPerMinute - beforeRequestsPerMinute, is(lessThanOrEqualTo(30)));

        log.info("Final stats: " + GsonFactory.createSnakeCase(testConfig.getConfig()).toJson(stats));
    }

    private long runBatch(long totalCalls, long batchCalls, long sleepMillis) throws InterruptedException, ExecutionException {
        int counter = 0;
        List<CompletableFuture<ConversationsListResponse>> futures = new ArrayList<>();
        while (counter < batchCalls) {
            CompletableFuture<ConversationsListResponse> response = slack.methodsAsync(botToken).conversationsList(r -> r.limit(1));
            futures.add(response);
            Thread.sleep(sleepMillis);
            counter++;
        }
        long successCount = 0L;
        for (CompletableFuture<ConversationsListResponse> f : futures) {
            f.get();
            successCount++;
        }
        assertThat(successCount, is(batchCalls));
        totalCalls += batchCalls;
        return totalCalls;
    }

    @Test
    public void chat_postMessage() throws Exception {
        long start = System.currentTimeMillis();

        List<CompletableFuture<ChatPostMessageResponse>> futures = new ArrayList<>();
        for (int i = 0; i < 65; i++) {
            final int idx = i;
            CompletableFuture<ChatPostMessageResponse> future = slack.methodsAsync(botToken)
                    .chatPostMessage(r -> r.channel("#random").text("hello * " + idx));
            futures.add(future);
            Thread.sleep(100L);
            // intentionally rate limited
        }
        String teamId = slack.methods().teamInfo(r -> r.token(botToken)).getTeam().getId();
        MethodsStats stats = testConfig.getMetricsDatastore().getStats(teamId);
        Long rateLimited = stats.getRateLimitedMethods().get("chat.postMessage_#random");
        while (rateLimited != null) {
            stats = testConfig.getMetricsDatastore().getStats(teamId);
            rateLimited = stats.getRateLimitedMethods().get("chat.postMessage_#random");
            log.info("stats - {}", GsonFactory.createCamelCase(testConfig.getConfig()).toJson(stats));
            Thread.sleep(2000L);
        }

        for (int idx = 0; idx < futures.size(); idx++) {
            CompletableFuture<ChatPostMessageResponse> future = futures.get(idx);
            try {
                ChatPostMessageResponse response = future.join();
                log.info("chat.postMessage - {}", response.getMessage().getText());
            } catch (Exception e) {
                log.info("error - {}", e.getMessage(), e);
            }
        }
        CompletableFuture<ChatPostMessageResponse> future = slack.methodsAsync(botToken)
                .chatPostMessage(r -> r.channel("#general").text("test"));
        ChatPostMessageResponse response = future.get();
        assertThat(response.getError(), is(nullValue()));

        long end = System.currentTimeMillis();
        long spentMillis = end - start;
        log.info("Spent time: {} milliseconds", spentMillis);

        stats = testConfig.getMetricsDatastore().getStats(teamId);
        log.info("Final stats - {}", GsonFactory.createCamelCase(testConfig.getConfig()).toJson(stats));

        assertThat(stats.getSuccessfulCalls().get("chat.postMessage"), is(greaterThanOrEqualTo(66L)));
        assertThat(stats.getRateLimitedMethods().get("chat.postMessage_#random"), is(nullValue()));
    }

}

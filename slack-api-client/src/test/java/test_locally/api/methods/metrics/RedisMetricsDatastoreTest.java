package test_locally.api.methods.metrics;

import com.github.fppt.jedismock.RedisServer;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.metrics.RedisMetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import redis.clients.jedis.JedisPool;
import util.MockSlackApiServer;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.*;
import static util.MockSlackApi.ValidToken;

public class RedisMetricsDatastoreTest {

    MockSlackApiServer slackApiServer = new MockSlackApiServer();
    Slack slack;
    RedisMetricsDatastore datastore;

    @Before
    public void setup() throws Exception {
        redisServer = RedisServer.newRedisServer();  // bind to a random port
        redisServer.start();
        String host = redisServer.getHost();
        if (host.equals("0.0.0.0")) { // workaround for macOS
            host = "127.0.0.1";
        }
        jedisPool = new JedisPool(host, redisServer.getBindPort());

        SlackConfig config = new SlackConfig();

        slackApiServer.start();
        config.setMethodsEndpointUrlPrefix(slackApiServer.getMethodsEndpointPrefix());

        datastore = new RedisMetricsDatastore("name", jedisPool);
        MethodsConfig methodsConfig = new MethodsConfig();
        methodsConfig.setMetricsDatastore(datastore);
        config.setMethodsConfig(methodsConfig);

        slack = Slack.getInstance(config);
    }

    @After
    public void tearDown() throws Exception {
        jedisPool.close();
        redisServer.stop();
        redisServer = null;

        slackApiServer.stop();
    }

    RedisServer redisServer;
    JedisPool jedisPool;

    @Test
    public void stats() throws ExecutionException, InterruptedException {
        AsyncMethodsClient client = slack.methodsAsync(ValidToken);
        for (int i = 0; i < 3; i++) {
            client.authTest(r -> r.token(ValidToken)).get();
        }
        Map<String, Map<String, RequestStats>> allStats = datastore.getAllStats();
        assertNotNull(allStats);
        assertEquals(1, allStats.get("DEFAULT_SINGLETON_EXECUTOR").size());

        RequestStats teamStats = allStats.get("DEFAULT_SINGLETON_EXECUTOR").values().iterator().next();
        assertEquals(3L, teamStats.getAllCompletedCalls().get("auth.test").longValue());
        assertEquals(3L, teamStats.getSuccessfulCalls().get("auth.test").longValue());
        assertEquals(3L, teamStats.getLastMinuteRequests().get("auth.test").longValue());

        String teamId = allStats.get("DEFAULT_SINGLETON_EXECUTOR").keySet().iterator().next();
        assertNotNull(datastore.getStats("DEFAULT_SINGLETON_EXECUTOR", teamId));
    }

    @Test
    public void job() throws ExecutionException, InterruptedException {
        AsyncMethodsClient client = slack.methodsAsync(ValidToken);
        for (int i = 0; i < 3; i++) {
            client.authTest(r -> r.token(ValidToken)).get();
        }
        String executor = MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
        datastore.setRateLimitedMethodRetryEpochMillis(executor, "T123", "auth.test", 123456L);
        RedisMetricsDatastore.MaintenanceJob job = new RedisMetricsDatastore.MaintenanceJob(datastore);
        job.run();
    }

    @Test
    public void increment() {
        String executor = MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
        datastore.incrementAllCompletedCalls(executor, "T123", "auth.test");
        datastore.incrementUnsuccessfulCalls(executor, "T123", "auth.test");
        datastore.incrementFailedCalls(executor, "T123", "auth.test");
        datastore.updateCurrentQueueSize(executor, "T123", "auth.test");
        datastore.updateNumberOfLastMinuteRequests(executor, "T123", "auth.test");
        datastore.setRateLimitedMethodRetryEpochMillis(executor, "T123", "auth.test", 123456L);
    }

    @Test
    public void threadGroupName() {
        RedisMetricsDatastore datastore1 = new RedisMetricsDatastore("app1", jedisPool);
        RedisMetricsDatastore datastore2 = new RedisMetricsDatastore("app2", jedisPool);
        assertEquals("slack-api-metrics:app1", datastore1.getThreadGroupName());
        assertEquals("slack-api-metrics:app2", datastore2.getThreadGroupName());
        assertNotEquals(datastore1.getThreadGroupName(), datastore2.getThreadGroupName());
    }

    @Test
    public void testGetNumberOfLastMinuteRequests() {
        String executorName = MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
        String teamId = "T123";
        String methodName = "auth.test";
        datastore.incrementAllCompletedCalls(executorName, teamId, methodName);
        datastore.incrementUnsuccessfulCalls(executorName, teamId, methodName);
        datastore.incrementFailedCalls(executorName, teamId, methodName);
        datastore.updateCurrentQueueSize(executorName, teamId, methodName);
        datastore.updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
        Integer numberOfRequests = datastore.getNumberOfLastMinuteRequests(executorName, teamId, methodName);
        assertNotNull(numberOfRequests);
        assertEquals(Integer.valueOf(1), numberOfRequests);
        datastore.updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
        numberOfRequests = datastore.getNumberOfLastMinuteRequests(executorName, teamId, methodName);
        assertNotNull(numberOfRequests);
        assertEquals(Integer.valueOf(2), numberOfRequests);
    }

    @Test
    public void testGetRateLimitedMethodRetryEpochMillis() {
        String executorName = MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
        String teamId = "T123";
        String methodName = "example.method";
        Long epochTimeMillis = System.currentTimeMillis() + 60000L;
        datastore.setRateLimitedMethodRetryEpochMillis(executorName, teamId, methodName, epochTimeMillis);
        Long retrievedEpochTimeMillis = datastore.getRateLimitedMethodRetryEpochMillis(executorName, teamId, methodName);
        assertNotNull(retrievedEpochTimeMillis);
        assertEquals(epochTimeMillis, retrievedEpochTimeMillis);
    }
    @Test
    public void testClose()
    {
        JedisPool jedisPool = Mockito.mock(JedisPool.class);
        ScheduledExecutorService scheduledExecutorService = Mockito.mock(ScheduledExecutorService.class);
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", jedisPool);
        try {
            datastore.close();
        } catch (Exception e) {
            fail("Exception should not be thrown during close.");
        }
        Mockito.verify(jedisPool).close();
    }

}

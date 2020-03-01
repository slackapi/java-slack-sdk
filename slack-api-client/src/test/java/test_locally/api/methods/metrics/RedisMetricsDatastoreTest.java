package test_locally.api.methods.metrics;

import com.github.fppt.jedismock.RedisServer;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.metrics.impl.RedisMetricsDatastore;
import config.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class RedisMetricsDatastoreTest {

    RedisServer server;
    JedisPool pool;

    @Before
    public void before() throws IOException {
        server = RedisServer.newRedisServer();  // bind to a random port
        server.start();
        pool = new JedisPool(server.getHost(), server.getBindPort());
    }

    @After
    public void after() {
        pool.close();
        server.stop();
        server = null;
    }

    @Test
    public void instantiation() {
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", pool);
        assertNotNull(datastore);
    }

    @Test
    public void stats() throws ExecutionException, InterruptedException {
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", pool);

        SlackConfig config = new SlackConfig();
        MethodsConfig methodsConfig = new MethodsConfig();
        methodsConfig.setMetricsDatastore(datastore);
        config.setMethodsConfig(methodsConfig);
        AsyncMethodsClient client = Slack.getInstance(config).methodsAsync();
        String token = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        for (int i = 0; i < 3; i++) {
            client.authTest(r -> r.token(token)).get();
        }
        Map<String, Map<String, MethodsStats>> allStats = datastore.getAllStats();
        assertNotNull(allStats);
        assertEquals(1, allStats.get("DEFAULT_SINGLETON_EXECUTOR").size());

        MethodsStats teamStats = allStats.get("DEFAULT_SINGLETON_EXECUTOR").values().iterator().next();
        assertEquals(3L, teamStats.getAllCompletedCalls().get("auth.test").longValue());
        assertEquals(3L, teamStats.getSuccessfulCalls().get("auth.test").longValue());
        assertEquals(3L, teamStats.getLastMinuteRequests().get("auth.test").longValue());

        String teamId = allStats.get("DEFAULT_SINGLETON_EXECUTOR").keySet().iterator().next();
        assertNotNull(datastore.getStats("DEFAULT_SINGLETON_EXECUTOR", teamId));
    }
}

package test_locally.api.methods.metrics;

import com.slack.api.methods.metrics.impl.RedisMetricsDatastore;
import org.junit.Test;
import org.mockito.Mockito;
import redis.clients.jedis.JedisPool;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RedisMetricsDatastoreTest {

    @Test
    public void instantiation() {
        JedisPool pool = Mockito.mock(JedisPool.class);
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", pool);
        assertNotNull(datastore);
    }

    @Test
    public void getAllStats() {
        JedisPool pool = Mockito.mock(JedisPool.class);
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", pool);
        assertNotNull(datastore.getAllStats());
    }

    @Test
    public void getStats() {
        JedisPool pool = Mockito.mock(JedisPool.class);
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", pool);
        assertNull(datastore.getStats("foo", "T123"));
    }

}

package test_locally.api.methods.metrics;

import com.slack.api.methods.metrics.impl.RedisMetricsDatastore;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RedisMetricsDatastoreTest {

    @Test
    public void instantiation() {
        RedisMetricsDatastore datastore = new RedisMetricsDatastore("name", null);
        assertNotNull(datastore);
    }
}

package test_locally.api.methods.metrics;

import com.slack.api.methods.metrics.impl.RedisMetricsDatastore;
import org.junit.Test;
import org.mockito.Mockito;

public class RedisMaintenanceJobTest {

    @Test
    public void test() {
        RedisMetricsDatastore datastore = Mockito.mock(RedisMetricsDatastore.class);
        RedisMetricsDatastore.MaintenanceJob job = new RedisMetricsDatastore.MaintenanceJob(datastore);
        job.run();
    }
}

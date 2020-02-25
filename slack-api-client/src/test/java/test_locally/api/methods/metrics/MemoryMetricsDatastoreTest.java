package test_locally.api.methods.metrics;

import com.slack.api.methods.MethodsStats;
import com.slack.api.methods.metrics.impl.MemoryMetricsDatastore;
import org.junit.Test;

import java.util.Map;

import static com.slack.api.methods.MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MemoryMetricsDatastoreTest {

    @Test
    public void getNumberOfNodes() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(3);
        assertEquals(3, datastore.getNumberOfNodes());
    }

    @Test
    public void getAllStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        Map<String, Map<String, MethodsStats>> allStats = datastore.getAllStats();
        assertNotNull(allStats);
    }

    @Test
    public void getStats_teamId() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        MethodsStats stats = datastore.getStats("T123");
        assertNotNull(stats);
    }

    @Test
    public void getStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        MethodsStats stats = datastore.getStats(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123");
        assertNotNull(stats);
    }

    @Test
    public void increment() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        datastore.incrementAllCompletedCalls(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage");
        datastore.incrementFailedCalls(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage");
        datastore.setRateLimitedMethodRetryEpochMillis(
                DEFAULT_SINGLETON_EXECUTOR_NAME, "T123", "chat.postMessage", 123L);
        datastore.addToWaitingMessageIds(
                DEFAULT_SINGLETON_EXECUTOR_NAME, null, "chat.postMessage", "id");
        datastore.deleteFromWaitingMessageIds(
                DEFAULT_SINGLETON_EXECUTOR_NAME, null, "chat.postMessage", "id");
    }
}

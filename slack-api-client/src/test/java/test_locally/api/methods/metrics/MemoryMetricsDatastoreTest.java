package test_locally.api.methods.metrics;

import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import org.junit.Test;

import java.util.Map;

import static com.slack.api.methods.MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME;
import static org.junit.Assert.*;

public class MemoryMetricsDatastoreTest {

    @Test
    public void getNumberOfNodes() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(3);
        assertEquals(3, datastore.getNumberOfNodes());
    }

    @Test
    public void getAllStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        Map<String, Map<String, RequestStats>> allStats = datastore.getAllStats();
        assertNotNull(allStats);
    }

    @Test
    public void getStats_teamId() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        RequestStats stats = datastore.getStats("T123");
        assertNotNull(stats);
    }

    @Test
    public void getStats() {
        MemoryMetricsDatastore datastore = new MemoryMetricsDatastore(1);
        RequestStats stats = datastore.getStats(DEFAULT_SINGLETON_EXECUTOR_NAME, "T123");
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

    @Test
    public void threadGroupName() {
        MemoryMetricsDatastore datastore1 = new MemoryMetricsDatastore(1);
        MemoryMetricsDatastore datastore2 = new MemoryMetricsDatastore(1);
        assertNotEquals(datastore1.getThreadGroupName(), datastore2.getThreadGroupName());
    }
}

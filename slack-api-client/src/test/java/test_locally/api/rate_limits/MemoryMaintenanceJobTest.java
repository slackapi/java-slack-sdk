package test_locally.api.rate_limits;

import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;
import com.slack.api.util.thread.DaemonThreadExecutorServiceFactory;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@Slf4j
public class MemoryMaintenanceJobTest {

    @Test
    public void verifyPerformance() {
        ExecutorService executor = DaemonThreadExecutorServiceFactory.createDaemonThreadPoolExecutor(
                "test", 1);

        try {
            MemoryMetricsDatastore store = new MemoryMetricsDatastore(
                    1, DaemonThreadExecutorServiceProvider.getInstance(), false, 0L);
            store.setTraceMode(false); // Enabling this affects the performance

            for (int testNum = 0; testNum < 5; testNum++) {
                // Data preparation
                for (int i = 0; i < 8000; i++) {
                    String teamId = "T" + i;
                    for (int methodNum = 0; methodNum < 50; methodNum++) {
                        String method = "chat." + methodNum;
                        for (int requestNum = 0; requestNum < 30; requestNum++) {
                            store.incrementAllCompletedCalls(MetricsDatastore.DEFAULT_SINGLETON_EXECUTOR_NAME, teamId, method);
                            store.incrementSuccessfulCalls(MetricsDatastore.DEFAULT_SINGLETON_EXECUTOR_NAME, teamId, method);
                            store.incrementUnsuccessfulCalls(MetricsDatastore.DEFAULT_SINGLETON_EXECUTOR_NAME, teamId, method);
                            store.incrementFailedCalls(MetricsDatastore.DEFAULT_SINGLETON_EXECUTOR_NAME, teamId, method);
                        }
                    }
                }
                BaseMemoryMetricsDatastore.MaintenanceJob job = new BaseMemoryMetricsDatastore.MaintenanceJob(store);
                Long firstSpentTime;
                {
                    long before = System.currentTimeMillis();
                    job.run();
                    firstSpentTime = System.currentTimeMillis() - before;
                    log.info("First execution: {} ms", firstSpentTime);
                    assertThat(firstSpentTime, is(lessThan(store.isTraceMode() ? 300L : 50L)));
                }

                // All the teams are marked as done
                {
                    long before = System.currentTimeMillis();
                    job.run();
                    long spentMillis = System.currentTimeMillis() - before;
                    log.info("Second execution: {} ms", spentMillis);
                    assertThat(spentMillis, is(lessThan(store.isTraceMode() ? 150L : 30L)));
                    assertThat(spentMillis, is(lessThanOrEqualTo(firstSpentTime)));
                }
            }
        } finally {
            executor.shutdownNow();
        }
    }
}

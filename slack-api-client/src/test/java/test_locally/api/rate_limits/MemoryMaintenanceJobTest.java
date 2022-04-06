package test_locally.api.rate_limits;

import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;
import com.slack.api.util.thread.DaemonThreadExecutorServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

@Slf4j
public class MemoryMaintenanceJobTest {

    @Test
    public void disablingStats() {
        ExecutorService executor = DaemonThreadExecutorServiceFactory.createDaemonThreadPoolExecutor(
                "test", 1);
        String executorName = "MemoryMaintenanceJobTest_disablingStats";

        try {
            MemoryMetricsDatastore store = new MemoryMetricsDatastore(1, false);
            store.setTraceMode(false); // Enabling this affects the performance

            for (int testNum = 0; testNum < 3; testNum++) {
                // Data preparation
                for (int i = 0; i < 10; i++) {
                    String teamId = "T" + i;
                    for (int methodNum = 0; methodNum < 10; methodNum++) {
                        String method = "chat." + methodNum;
                        for (int requestNum = 0; requestNum < 5; requestNum++) {
                            store.incrementAllCompletedCalls(executorName, teamId, method);
                            store.incrementSuccessfulCalls(executorName, teamId, method);
                            store.incrementUnsuccessfulCalls(executorName, teamId, method);
                            store.incrementFailedCalls(executorName, teamId, method);
                        }
                    }
                }
            }
            BaseMemoryMetricsDatastore.MaintenanceJob job = new BaseMemoryMetricsDatastore.MaintenanceJob(store);
            assertThat(store.getAllStats().toString(), store.getAllStats().get("METHODS/" + executorName), is(nullValue()));

            long before = System.currentTimeMillis();
            job.run();
            long spentTime = System.currentTimeMillis() - before;
            log.info("Execution time: {} ms", spentTime);
            assertThat(spentTime, is(lessThan(10L)));
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    public void maintenanceJobPerformance() throws Exception {
        ExecutorService executor = DaemonThreadExecutorServiceFactory.createDaemonThreadPoolExecutor(
                "test", 1);
        String executorName = "MemoryMaintenanceJobTest_maintenanceJobPerformance";

        try {
            MemoryMetricsDatastore store = new MemoryMetricsDatastore(1, true, 60_000L);
            store.setTraceMode(false); // Enabling this affects the performance

            // Wait for the initial maintenance
            Thread.sleep(1500L);

            for (int testNum = 0; testNum < 5; testNum++) {
                // Data preparation
                for (int i = 0; i < 8000; i++) {
                    String teamId = "T" + i;
                    for (int methodNum = 0; methodNum < 50; methodNum++) {
                        String method = "chat." + methodNum;
                        for (int requestNum = 0; requestNum < 30; requestNum++) {
                            store.incrementAllCompletedCalls(executorName, teamId, method);
                            store.incrementSuccessfulCalls(executorName, teamId, method);
                            store.incrementUnsuccessfulCalls(executorName, teamId, method);
                            store.incrementFailedCalls(executorName, teamId, method);
                        }
                    }
                }
                assertThat(store.getAllStats().toString(), store.getAllStats().get("METHODS/" + executorName), is(notNullValue()));

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
                int failureCount = 0;
                while (failureCount < 10) {
                    {
                        long before = System.currentTimeMillis();
                        job.run();
                        long spentMillis = System.currentTimeMillis() - before;
                        log.info("Second execution: {} ms", spentMillis);
                        if (spentMillis > (store.isTraceMode() ? 150L : 30L)) {
                            failureCount++;
                        }
                        if (spentMillis > firstSpentTime) {
                            failureCount++;
                        }
                        if (failureCount == 0) {
                            break;
                        }
                    }
                }
                assertThat(failureCount, is(lessThan(10)));
            }
        } finally {
            executor.shutdownNow();
        }
    }
}

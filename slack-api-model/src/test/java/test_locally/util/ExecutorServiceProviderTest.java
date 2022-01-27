package test_locally.util;

import com.slack.api.util.thread.ExecutorServiceProvider;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.Assert.assertTrue;

public class ExecutorServiceProviderTest {

    @Test
    public void getInstanceId() {
        ExecutorServiceProvider provider = new ExecutorServiceProvider() {
            @Override
            public ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize) {
                return null;
            }

            @Override
            public ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName) {
                return null;
            }
        };
        assertTrue(provider.getInstanceId().matches("test_locally.util.ExecutorServiceFactoryTest\\$1\\@\\w+$"));
    }
}

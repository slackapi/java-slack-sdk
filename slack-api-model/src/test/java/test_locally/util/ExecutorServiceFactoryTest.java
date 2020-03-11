package test_locally.util;

import com.slack.api.util.thread.ExecutorServiceFactory;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class ExecutorServiceFactoryTest {

    @Test
    public void createDaemonThreadScheduledExecutor() throws InterruptedException {
        ScheduledExecutorService service = ExecutorServiceFactory.createDaemonThreadScheduledExecutor("test");
        AtomicBoolean isDaemon = new AtomicBoolean(false);
        service.schedule(() -> isDaemon.set(Thread.currentThread().isDaemon()), 1, TimeUnit.MILLISECONDS);

        Thread.sleep(50L);

        assertTrue(isDaemon.get());
    }

    @Test
    public void createDaemonThreadPoolExecutor() throws InterruptedException {
        ExecutorService service = ExecutorServiceFactory.createDaemonThreadPoolExecutor("test", 3);

        AtomicBoolean isDaemon = new AtomicBoolean(false);
        service.submit(() -> isDaemon.set(Thread.currentThread().isDaemon()));

        Thread.sleep(50L);

        assertTrue(isDaemon.get());
    }
}

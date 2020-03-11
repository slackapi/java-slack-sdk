package test_locally.util;

import com.slack.api.util.thread.DaemonThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Slf4j
public class DaemonThreadFactoryTest {

    @Test
    public void test() throws InterruptedException {
        DaemonThreadFactory factory = new DaemonThreadFactory("awesome-group");
        Thread thread = factory.newThread(() -> log.info("This is a test"));
        thread.start();

        thread.join();

        assertTrue(thread.isDaemon());
        assertFalse(thread.isAlive());
    }

}

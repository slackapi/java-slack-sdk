package test_locally.api.scim;

import com.slack.api.scim.SCIMConfig;
import com.slack.api.scim.impl.ThreadPools;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AsyncThreadPoolsTest {

    @Test
    public void getDefault() {
        ExecutorService service = ThreadPools.getDefault(SCIMConfig.DEFAULT_SINGLETON);
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void getOrCreate() {
        SCIMConfig config = new SCIMConfig();
        config.setExecutorName("getOrCreate" + System.currentTimeMillis());
        Map<String, Integer> poolSizes = new HashMap<>();
        poolSizes.put("E111", 1);
        config.setCustomThreadPoolSizes(poolSizes);
        ExecutorService service = ThreadPools.getOrCreate(config, "E111");
        assertThat(service, is(notNullValue()));
    }

}

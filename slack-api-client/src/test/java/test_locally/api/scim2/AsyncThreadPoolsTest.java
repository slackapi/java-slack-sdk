package test_locally.api.scim2;

import com.slack.api.scim2.SCIM2Config;
import com.slack.api.scim2.impl.ThreadPools;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AsyncThreadPoolsTest {

    @Test
    public void getDefault() {
        ExecutorService service = ThreadPools.getDefault(SCIM2Config.DEFAULT_SINGLETON);
        assertThat(service, is(notNullValue()));
    }

    @Test
    public void getOrCreate() {
        SCIM2Config config = new SCIM2Config();
        config.setExecutorName("getOrCreate" + System.currentTimeMillis());
        Map<String, Integer> poolSizes = new HashMap<>();
        poolSizes.put("E111", 1);
        config.setCustomThreadPoolSizes(poolSizes);
        ExecutorService service = ThreadPools.getOrCreate(config, "E111");
        assertThat(service, is(notNullValue()));
    }

}

package test_locally.api.methods;

import com.slack.api.methods.MethodsConfig;
import org.junit.Test;

import java.util.Collections;

public class MethodsConfigTest {

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setCustomThreadPoolSizes() {
        MethodsConfig.DEFAULT_SINGLETON.setCustomThreadPoolSizes(Collections.emptyMap());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setMetricsDatastore() {
        MethodsConfig.DEFAULT_SINGLETON.setMetricsDatastore(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setStatsEnabled() {
        MethodsConfig.DEFAULT_SINGLETON.setStatsEnabled(false);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setExecutorName() {
        MethodsConfig.DEFAULT_SINGLETON.setExecutorName(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setMaxIdleMills() {
        MethodsConfig.DEFAULT_SINGLETON.setMaxIdleMills(123);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutable_singleton_setDefaultThreadPoolSize() {
        MethodsConfig.DEFAULT_SINGLETON.setDefaultThreadPoolSize(123);
    }

}

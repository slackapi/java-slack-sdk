package test_locally.api.methods;

import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.MethodsStats;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MethodsStatsTest {

    @Test
    public void lookup() {
        MethodsStats stats = MethodsStats.lookup(MethodsConfig.DEFAULT_SINGLETON_EXECUTOR_NAME, "T123");
        assertThat(stats, is(notNullValue()));
    }

    @Test
    public void lookup_null() {
        assertThat(MethodsStats.lookup(null, "T123"), is(nullValue()));
        assertThat(MethodsStats.lookup("foo", null), is(nullValue()));
        assertThat(MethodsStats.lookup(null, null), is(nullValue()));
    }

}
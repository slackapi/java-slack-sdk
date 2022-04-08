package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class SlackConfigTest {

    @Test
    public void instantiation() {
        SlackConfig config = new SlackConfig();
        assertThat(config, is(notNullValue()));
    }

    @Test
    public void synchronizeMetricsDatabases() {
        SlackConfig config = new SlackConfig();
        config.synchronizeMetricsDatabases();
    }

    @Test
    public void immutableDefaultConfig() {
        try {
            SlackConfig.DEFAULT.setFailOnUnknownProperties(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setPrettyResponseLoggingEnabled(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setLibraryMaintainerMode(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setTokenExistenceVerificationEnabled(false);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setHttpClientResponseHandlers(Collections.emptyList());
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setAuditEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setMethodsEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setScimEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setStatusEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setLegacyStatusEndpointUrlPrefix("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setMethodsConfig(new MethodsConfig());
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setProxyUrl("");
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setAuditConfig(null);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setSCIMConfig(null);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
        try {
            SlackConfig.DEFAULT.setExecutorServiceProvider(null);
            fail();
        } catch (UnsupportedOperationException ignored) {
        }
    }

    @Test
    public void customizeExecutorServiceProvider() throws Exception {
        // the default
        assertThat(Slack.getInstance().getConfig().getExecutorServiceProvider(),
                is(DaemonThreadExecutorServiceProvider.getInstance()));

        // the customized one
        SlackConfig config = new SlackConfig();
        ExecutorServiceProvider custom = new ExecutorServiceProvider() {
            @Override
            public ExecutorService createThreadPoolExecutor(String threadGroupName, int poolSize) {
                return DaemonThreadExecutorServiceProvider.getInstance()
                        .createThreadPoolExecutor(threadGroupName, poolSize);
            }

            @Override
            public ScheduledExecutorService createThreadScheduledExecutor(String threadGroupName) {
                return DaemonThreadExecutorServiceProvider.getInstance()
                        .createThreadScheduledExecutor(threadGroupName);
            }
        };
        config.setExecutorServiceProvider(custom);

        try (Slack slack = Slack.getInstance(config)) {
            assertThat(slack.getConfig().getExecutorServiceProvider(), is(custom));
            assertThat(slack.getConfig().getMethodsConfig().getExecutorServiceProvider(), is(custom));
            assertThat(slack.getConfig().getAuditConfig().getExecutorServiceProvider(), is(custom));
            assertThat(slack.getConfig().getSCIMConfig().getExecutorServiceProvider(), is(custom));
        }
    }

}

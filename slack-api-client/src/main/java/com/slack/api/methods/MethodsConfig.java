package com.slack.api.methods;

import com.slack.api.methods.metrics.MemoryMetricsDatastore;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for {@link MethodsClient}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodsConfig {

    /**
     * If you don't have a special reason, we recommend going with the singleton executor to track all the traffic
     * your app generated towards the Slack Platform in one place (= in one metrics datastore).
     */
    public static final String DEFAULT_SINGLETON_EXECUTOR_NAME = MetricsDatastore.DEFAULT_SINGLETON_EXECUTOR_NAME;

    /**
     * The default configuration. It's not allowed to modify this runtime for any reasons.
     */
    public static final MethodsConfig DEFAULT_SINGLETON = new MethodsConfig() {

        void throwException() {
            throw new UnsupportedOperationException("This config is immutable");
        }

        @Override
        public void setStatsEnabled(boolean statsEnabled) {
            throwException();
        }

        @Override
        public void setExecutorName(String executorName) {
            throwException();
        }

        @Override
        public void setMaxIdleMills(int maxIdleMills) {
            throwException();
        }

        @Override
        public void setDefaultThreadPoolSize(int defaultThreadPoolSize) {
            throwException();
        }

        @Override
        public void setMetricsDatastore(MetricsDatastore metricsDatastore) {
            throwException();
        }

        @Override
        public void setCustomThreadPoolSizes(Map<String, Integer> customThreadPoolSizes) {
            throwException();
        }

        @Override
        public void setExecutorServiceProvider(ExecutorServiceProvider executorServiceProvider) {
            throwException();
        }
    };

    @Builder.Default
    private boolean statsEnabled = true;

    /**
     * If you need to have multiple executors in the same Slack app, name this accordingly.
     */
    @Builder.Default
    private String executorName = DEFAULT_SINGLETON_EXECUTOR_NAME;

    /**
     * The max period to keep asynchronous API method calls idle.
     */
    @Builder.Default
    private int maxIdleMills = 60 * 60 * 1000;

    /**
     * The default thread pool size used for asynchronous API method calls.
     */
    @Builder.Default
    private int defaultThreadPoolSize = 5;

    @Builder.Default
    private ExecutorServiceProvider executorServiceProvider = DaemonThreadExecutorServiceProvider.getInstance();

    /**
     * Team ID -> thread pool size
     */
    @Builder.Default
    private Map<String, Integer> customThreadPoolSizes = new HashMap<>();

    /**
     * The metrics datastore to track the traffic associated to this executor name.
     */
    @Builder.Default
    private MetricsDatastore metricsDatastore = new MemoryMetricsDatastore(1);

}

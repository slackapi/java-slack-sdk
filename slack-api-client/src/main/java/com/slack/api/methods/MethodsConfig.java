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
     * Creates a configuration with the existing automatic HTTP 429 retry behavior.
     */
    public MethodsConfig(boolean statsEnabled, String executorName, int maxIdleMills,
                         int defaultThreadPoolSize, ExecutorServiceProvider executorServiceProvider,
                         Map<String, Integer> customThreadPoolSizes, MetricsDatastore metricsDatastore,
                         MethodsCustomRateLimitResolver customRateLimitResolver) {
        this(statsEnabled, executorName, maxIdleMills, defaultThreadPoolSize, executorServiceProvider,
                customThreadPoolSizes, metricsDatastore, customRateLimitResolver, false);
    }


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

        @Override
        public void setCustomRateLimitResolver(MethodsCustomRateLimitResolver customRateLimitResolver) {
            throwException();
        }

        @Override
        public void setRejectRateLimitedCalls(boolean rejectRateLimitedCalls) {
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

    /**
     * Custom handlers for adjusting rate limits.
     */
    @Builder.Default
    private MethodsCustomRateLimitResolver customRateLimitResolver = MethodsCustomRateLimitResolver.DEFAULT;

    /**
     * When true, asynchronous Web API calls fail on HTTP 429 instead of being retried.
     * The resulting {@link java.util.concurrent.CompletableFuture} fails with a
     * {@link MethodsCompletionException} wrapping the {@link SlackApiException},
     * including the response status and {@code Retry-After} header.
     * This does not disable queueing or change synchronous API calls.
     * The default is false, preserving automatic retries for rate-limited calls.
     */
    @Builder.Default
    private boolean rejectRateLimitedCalls = false;

}

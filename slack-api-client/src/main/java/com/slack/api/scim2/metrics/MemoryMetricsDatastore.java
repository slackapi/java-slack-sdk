package com.slack.api.scim2.metrics;

import com.slack.api.rate_limits.RateLimiter;
import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;
import com.slack.api.scim2.SCIM2ApiResponse;
import com.slack.api.scim2.impl.AsyncExecutionSupplier;
import com.slack.api.scim2.impl.AsyncRateLimitQueue;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;

public class MemoryMetricsDatastore extends BaseMemoryMetricsDatastore<
        AsyncExecutionSupplier<? extends SCIM2ApiResponse>, AsyncRateLimitQueue.SCIMMessage> {

    public MemoryMetricsDatastore(int numberOfNodes) {
        super(numberOfNodes);
    }

    public MemoryMetricsDatastore(
            int numberOfNodes,
            boolean statsEnabled
    ) {
        super(numberOfNodes, DaemonThreadExecutorServiceProvider.getInstance(), statsEnabled, RateLimiter.DEFAULT_BACKGROUND_JOB_INTERVAL_MILLIS);
    }

    public MemoryMetricsDatastore(
            int numberOfNodes,
            boolean statsEnabled,
            long backgroundJobIntervalMilliseconds
    ) {
        super(numberOfNodes, DaemonThreadExecutorServiceProvider.getInstance(), statsEnabled, backgroundJobIntervalMilliseconds);
    }

    public MemoryMetricsDatastore(
            int numberOfNodes,
            ExecutorServiceProvider executorServiceProvider,
            boolean statsEnabled,
            long backgroundJobIntervalMilliseconds
    ) {
        super(numberOfNodes, executorServiceProvider, statsEnabled, backgroundJobIntervalMilliseconds);
    }

    @Override
    protected String getMetricsType() {
        return "SCIM";
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

}

package com.slack.api.methods.metrics;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.methods.impl.AsyncExecutionSupplier;
import com.slack.api.methods.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;

public class MemoryMetricsDatastore extends BaseMemoryMetricsDatastore<AsyncExecutionSupplier<? extends SlackApiResponse>, AsyncRateLimitQueue.Message> {

    public MemoryMetricsDatastore(int numberOfNodes) {
        super(numberOfNodes);
    }

    @Override
    protected String getMetricsType() {
        return "METHODS";
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

}
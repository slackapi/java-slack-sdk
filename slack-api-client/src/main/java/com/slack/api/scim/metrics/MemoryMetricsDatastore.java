package com.slack.api.scim.metrics;

import com.slack.api.rate_limits.metrics.impl.BaseMemoryMetricsDatastore;
import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.impl.AsyncExecutionSupplier;
import com.slack.api.scim.impl.AsyncRateLimitQueue;

public class MemoryMetricsDatastore extends BaseMemoryMetricsDatastore<
        AsyncExecutionSupplier<? extends SCIMApiResponse>, AsyncRateLimitQueue.SCIMMessage> {

    public MemoryMetricsDatastore(int numberOfNodes) {
        super(numberOfNodes);
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

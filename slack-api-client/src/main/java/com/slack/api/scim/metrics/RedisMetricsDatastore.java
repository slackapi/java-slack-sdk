package com.slack.api.scim.metrics;

import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.impl.AsyncExecutionSupplier;
import com.slack.api.scim.impl.AsyncRateLimitQueue;
import com.slack.api.util.thread.ExecutorServiceProvider;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<
        AsyncExecutionSupplier<? extends SCIMApiResponse>, AsyncRateLimitQueue.SCIMMessage> {

    public RedisMetricsDatastore(String appName, JedisPool jedisPool) {
        super(appName, jedisPool);
    }

    public RedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            boolean cleanerEnabled,
            long cleanerExecutionIntervalMilliseconds
    ) {
        super(appName, jedisPool, cleanerEnabled, cleanerExecutionIntervalMilliseconds);
    }

    public RedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            ExecutorServiceProvider executorServiceProvider,
            boolean cleanerEnabled,
            long cleanerExecutionIntervalMilliseconds
    ) {
        super(appName, jedisPool, executorServiceProvider, cleanerEnabled, cleanerExecutionIntervalMilliseconds);
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

    @Override
    protected String getMetricsType() {
        return "SCIM";
    }

}

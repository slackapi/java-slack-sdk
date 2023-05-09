package com.slack.api.scim2.metrics;

import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.scim2.SCIM2ApiResponse;
import com.slack.api.scim2.impl.AsyncExecutionSupplier;
import com.slack.api.scim2.impl.AsyncRateLimitQueue;
import com.slack.api.util.thread.ExecutorServiceProvider;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<
        AsyncExecutionSupplier<? extends SCIM2ApiResponse>, AsyncRateLimitQueue.SCIMMessage> {

    public RedisMetricsDatastore(String appName, JedisPool jedisPool) {
        super(appName, jedisPool);
    }

    public RedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            boolean statsEnabled,
            long backgroundJobIntervalMilliseconds
    ) {
        super(appName, jedisPool, statsEnabled, backgroundJobIntervalMilliseconds);
    }

    public RedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            ExecutorServiceProvider executorServiceProvider,
            boolean statsEnabled,
            long backgroundJobIntervalMilliseconds
    ) {
        super(appName, jedisPool, executorServiceProvider, statsEnabled, backgroundJobIntervalMilliseconds);
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

package com.slack.api.methods.metrics;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.methods.impl.AsyncExecutionSupplier;
import com.slack.api.methods.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import com.slack.api.util.thread.ExecutorServiceProvider;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<AsyncExecutionSupplier<? extends SlackApiResponse>, AsyncRateLimitQueue.Message> {

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
    public RateLimitQueue<AsyncExecutionSupplier<? extends SlackApiResponse>, AsyncRateLimitQueue.Message> getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

    @Override
    protected String getMetricsType() {
        return "METHODS";
    }
}

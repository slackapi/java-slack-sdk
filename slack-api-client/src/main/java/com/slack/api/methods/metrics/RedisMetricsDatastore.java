package com.slack.api.methods.metrics;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.methods.impl.AsyncExecutionSupplier;
import com.slack.api.methods.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<AsyncExecutionSupplier<? extends SlackApiResponse>, AsyncRateLimitQueue.Message> {

    public RedisMetricsDatastore(String appName, JedisPool jedisPool) {
        super(appName, jedisPool);
    }

    @Override
    public RateLimitQueue<AsyncExecutionSupplier<? extends SlackApiResponse>, AsyncRateLimitQueue.Message> getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }
}

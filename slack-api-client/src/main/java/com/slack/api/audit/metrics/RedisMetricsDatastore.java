package com.slack.api.audit.metrics;

import com.slack.api.audit.AuditApiResponse;
import com.slack.api.audit.impl.AsyncExecutionSupplier;
import com.slack.api.audit.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<
        AsyncExecutionSupplier<? extends AuditApiResponse>, AsyncRateLimitQueue.AuditMessage> {

    public RedisMetricsDatastore(String appName, JedisPool jedisPool) {
        super(appName, jedisPool);
    }

    @Override
    public AsyncRateLimitQueue getRateLimitQueue(String executorName, String teamId) {
        return AsyncRateLimitQueue.get(executorName, teamId);
    }

}

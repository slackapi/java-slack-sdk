package com.slack.api.audit.metrics;

import com.slack.api.audit.AuditApiResponse;
import com.slack.api.audit.impl.AsyncExecutionSupplier;
import com.slack.api.audit.impl.AsyncRateLimitQueue;
import com.slack.api.rate_limits.metrics.impl.BaseRedisMetricsDatastore;
import com.slack.api.util.thread.ExecutorServiceProvider;
import redis.clients.jedis.JedisPool;

public class RedisMetricsDatastore extends BaseRedisMetricsDatastore<
        AsyncExecutionSupplier<? extends AuditApiResponse>, AsyncRateLimitQueue.AuditMessage> {

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
        return "AUDIT_LOGS";
    }

}

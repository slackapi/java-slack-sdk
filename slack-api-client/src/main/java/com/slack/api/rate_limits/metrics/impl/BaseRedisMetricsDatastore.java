package com.slack.api.rate_limits.metrics.impl;

import com.slack.api.rate_limits.metrics.LastMinuteRequests;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.rate_limits.queue.QueueMessage;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import com.slack.api.util.thread.DaemonThreadExecutorServiceProvider;
import com.slack.api.util.thread.ExecutorServiceProvider;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

public abstract class BaseRedisMetricsDatastore<SUPPLIER, MSG extends QueueMessage>
        implements MetricsDatastore, AutoCloseable {

    public static long DEFAULT_CLEANER_EXECUTION_INTERVAL_MILLISECONDS = 1_000L;

    private final String appName;
    private final JedisPool jedisPool;
    private ExecutorServiceProvider executorServiceProvider;
    private ScheduledExecutorService cleanerExecutor;
    private boolean traceMode;
    private boolean cleanerEnabled;
    private long cleanerExecutionIntervalMilliseconds;

    public BaseRedisMetricsDatastore(String appName, JedisPool jedisPool) {
        this(
                appName,
                jedisPool,
                DaemonThreadExecutorServiceProvider.getInstance(),
                true,
                DEFAULT_CLEANER_EXECUTION_INTERVAL_MILLISECONDS
        );
    }

    public BaseRedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            ExecutorServiceProvider executorServiceProvider
    ) {
        this(
                appName,
                jedisPool,
                executorServiceProvider,
                true,
                DEFAULT_CLEANER_EXECUTION_INTERVAL_MILLISECONDS
        );
    }

    public BaseRedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            boolean cleanerEnabled
    ) {
        this(
                appName,
                jedisPool,
                DaemonThreadExecutorServiceProvider.getInstance(),
                cleanerEnabled,
                DEFAULT_CLEANER_EXECUTION_INTERVAL_MILLISECONDS
        );
    }

    public BaseRedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            boolean cleanerEnabled,
            long cleanerExecutionIntervalMilliseconds
    ) {
        this(
                appName,
                jedisPool,
                DaemonThreadExecutorServiceProvider.getInstance(),
                cleanerEnabled,
                cleanerExecutionIntervalMilliseconds
        );
    }

    public BaseRedisMetricsDatastore(
            String appName,
            JedisPool jedisPool,
            ExecutorServiceProvider executorServiceProvider,
            boolean cleanerEnabled,
            long cleanerExecutionIntervalMilliseconds
    ) {
        this.appName = appName;
        this.jedisPool = jedisPool;
        this.executorServiceProvider = executorServiceProvider;
        this.cleanerEnabled = cleanerEnabled;
        this.cleanerExecutionIntervalMilliseconds = cleanerExecutionIntervalMilliseconds;
        if (this.cleanerEnabled) {
            this.initializeCleanerExecutor();
        }
    }

    public abstract RateLimitQueue<SUPPLIER, MSG> getRateLimitQueue(String executorName, String teamId);

    public Jedis jedis() {
        return jedisPool.getResource();
    }

    protected void initializeCleanerExecutor() {
        if (this.cleanerExecutor != null) {
            // Abandon the running one first
            this.cleanerExecutor.shutdown();
        }
        this.cleanerExecutor = getExecutorServiceProvider().createThreadScheduledExecutor(getThreadGroupName());
        this.cleanerExecutor.scheduleAtFixedRate(
                new MaintenanceJob(this),
                1000,
                this.cleanerExecutionIntervalMilliseconds,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void close() throws Exception {
        this.cleanerExecutor.shutdown();
        this.jedisPool.destroy();
    }

    // Added in v1.19
    protected abstract String getMetricsType();

    public String getThreadGroupName() {
        return "slack-api-metrics:" + this.appName;
    }

    @Override
    public ExecutorServiceProvider getExecutorServiceProvider() {
        return this.executorServiceProvider;
    }

    @Override
    public void setExecutorServiceProvider(ExecutorServiceProvider executorServiceProvider) {
        this.executorServiceProvider = executorServiceProvider;
        if (cleanerEnabled) {
            initializeCleanerExecutor();
        }
    }

    @Override
    public boolean isTraceMode() {
        return this.traceMode;
    }

    @Override
    public void setTraceMode(boolean isTraceMode) {
        this.traceMode = isTraceMode;
    }

    private void addToStatsKeyIndices(Jedis jedis, String statsKey) {
        jedis.sadd("StatsKeys", statsKey);
    }

    private void addToLastMinuteRequestsKeyIndices(Jedis jedis, String statsKey) {
        jedis.sadd("LastMinuteRequestsKeys", statsKey);
    }

    private void addToMessageIdsKeyIndices(Jedis jedis, String statsKey) {
        jedis.sadd("MessageIdsKeys", statsKey);
    }

    @Override
    public Map<String, Map<String, RequestStats>> getAllStats() {
        Map<String, Map<String, RequestStats>> result = new HashMap<>();
        try (Jedis jedis = jedis()) {
            if (jedis == null) {
                return result;
            }
            Set<String> statsKeys = jedis.smembers("StatsKeys");
            if (statsKeys == null) {
                return result;
            }
            for (String statsKey : statsKeys) {
                String[] elements = statsKey.split("@");
                if (elements.length < 6) {
                    continue;
                }
                String executorName = elements[2];
                String teamId = elements[3];
                String methodName = elements[4];
                String operation = elements[5];
                if (!result.containsKey(executorName)) {
                    result.put(executorName, new HashMap<>());
                }
                if (!result.get(executorName).containsKey(teamId)) {
                    result.get(executorName).put(teamId, new RequestStats());
                }
                RequestStats stats = result.get(executorName).get(teamId);
                String value = jedis.get(statsKey);
                if (value != null && !value.trim().isEmpty()) {
                    if (operation.equals("AllCompletedCalls")) {
                        stats.getAllCompletedCalls().put(methodName, Long.valueOf(value));
                    } else if (operation.equals("SuccessfulCalls")) {
                        stats.getSuccessfulCalls().put(methodName, Long.valueOf(value));
                    } else if (operation.equals("UnsuccessfulCalls")) {
                        stats.getUnsuccessfulCalls().put(methodName, Long.valueOf(value));
                    } else if (operation.equals("FailedCalls")) {
                        stats.getFailedCalls().put(methodName, Long.valueOf(value));
                    } else if (operation.equals("CurrentQueueSize")) {
                        stats.getCurrentQueueSize().put(methodName, Integer.valueOf(value));
                    } else if (operation.equals("LastMinuteRequests")) {
                        stats.getLastMinuteRequests().put(methodName, Integer.valueOf(value));
                    } else if (operation.equals("RateLimitedMethods")) {
                        stats.getRateLimitedMethods().put(methodName, Long.valueOf(value));
                    } else if (operation.equals("LastRequestTimestampMillis")) {
                        stats.setLastRequestTimestampMillis(Long.valueOf(value));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public RequestStats getStats(String executorName, String teamId) {
        Map<String, RequestStats> executor = getAllStats().get(executorName);
        return executor != null ? executor.get(teamId) : null;
    }

    private String escapeDelimiter(String executorName) {
        return executorName.replaceAll("@", "_");
    }

    private String toStatsKey(Jedis jedis, String operation, String executorName, String teamId, String methodName) {
        String key = escapeDelimiter(appName) + "@Stats@" + escapeDelimiter(executorName) + "@" + teamId + "@" + methodName + "@" + operation;
        addToStatsKeyIndices(jedis, key);
        return key;
    }

    private String toLastMinuteRequestsKey(Jedis jedis, String executorName, String teamId, String methodName) {
        String key = escapeDelimiter(appName) + "@LastMinuteRequests@" + escapeDelimiter(executorName) + "@" + teamId + "@" + methodName;
        addToLastMinuteRequestsKeyIndices(jedis, key);
        return key;
    }

    private String toWaitingMessageIdsKey(Jedis jedis, String executorName, String teamId, String methodName) {
        String key = escapeDelimiter(appName) + "@WaitingMessageIds@" + escapeDelimiter(executorName) + "@" + teamId + "@" + methodName;
        addToMessageIdsKeyIndices(jedis, key);
        return key;
    }

    @Override
    public void incrementAllCompletedCalls(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            jedis.incr(toStatsKey(jedis, "AllCompletedCalls", executorName, teamId, methodName));
        }
    }

    @Override
    public void incrementSuccessfulCalls(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            jedis.set(toStatsKey(jedis, "LastRequestTimestampMillis", executorName, teamId, methodName),
                    String.valueOf(System.currentTimeMillis()));
            jedis.incr(toStatsKey(jedis, "SuccessfulCalls", executorName, teamId, methodName));
        }
    }

    @Override
    public void incrementUnsuccessfulCalls(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            jedis.set(toStatsKey(jedis, "LastRequestTimestampMillis", executorName, teamId, methodName),
                    String.valueOf(System.currentTimeMillis()));
            jedis.incr(toStatsKey(jedis, "UnsuccessfulCalls", executorName, teamId, methodName));
        }
    }

    @Override
    public void incrementFailedCalls(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            jedis.set(toStatsKey(jedis, "LastRequestTimestampMillis", executorName, teamId, methodName),
                    String.valueOf(System.currentTimeMillis()));
            jedis.incr(toStatsKey(jedis, "FailedCalls", executorName, teamId, methodName));
        }
    }

    @Override
    public void updateCurrentQueueSize(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            String key = toWaitingMessageIdsKey(jedis, executorName, teamId, methodName);
            Integer totalSize = Math.toIntExact(jedis.llen(key));
            RateLimitQueue<SUPPLIER, MSG> queue = getRateLimitQueue(executorName, teamId);
            if (queue != null) {
                totalSize += queue.getCurrentActiveQueueSize(methodName);
            }
            setCurrentQueueSize(executorName, teamId, methodName, totalSize);
        }
    }

    @Override
    public void setCurrentQueueSize(String executorName, String teamId, String methodName, Integer value) {
        try (Jedis jedis = jedis()) {
            jedis.set(toStatsKey(jedis, "CurrentQueueSize", executorName, teamId, methodName), "" + value);
        }
    }

    @Override
    public void updateNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            String key = toLastMinuteRequestsKey(jedis, executorName, teamId, methodName);
            long oneMinuteAgo = System.currentTimeMillis() - 60000L;
            for (String str : jedis.lrange(key, 0, jedis.llen(key))) {
                long millis = Long.valueOf(str);
                if (millis < oneMinuteAgo) {
                    jedis.lrem(key, 1, str);
                }
            }
            setNumberOfLastMinuteRequests(executorName, teamId, methodName, Math.toIntExact(jedis.llen(key)));
        }
    }

    @Override
    public Integer getNumberOfLastMinuteRequests(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            String key = toLastMinuteRequestsKey(jedis, executorName, teamId, methodName);
            return Math.toIntExact(jedis.llen(key));
        }
    }

    @Override
    public void setNumberOfLastMinuteRequests(String executorName, String teamId, String methodName, Integer value) {
        try (Jedis jedis = jedis()) {
            jedis.set(toStatsKey(jedis, "LastMinuteRequests", executorName, teamId, methodName), "" + value);
        }
    }

    @Override
    public Long getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            String key = toStatsKey(jedis, "RateLimitedMethods", executorName, teamId, methodName);
            String value = jedis.get(key);
            return value != null ? Long.valueOf(value) : null;
        }
    }

    @Override
    public void setRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName, Long epochTimeMillis) {
        try (Jedis jedis = jedis()) {
            String key = toStatsKey(jedis, "RateLimitedMethods", executorName, teamId, methodName);
            jedis.set(key, String.valueOf(epochTimeMillis));
        }
    }

    @Override
    public void addToLastMinuteRequests(String executorName, String teamId, String methodName, Long currentMillis) {
        try (Jedis jedis = jedis()) {
            String key = toLastMinuteRequestsKey(jedis, executorName, teamId, methodName);
            jedis.rpush(key, String.valueOf(currentMillis));
            setNumberOfLastMinuteRequests(executorName, teamId, methodName, Math.toIntExact(jedis.llen(key)));
        }
    }

    @Override
    public LastMinuteRequests getLastMinuteRequests(String executorName, String teamId, String methodName) {
        try (Jedis jedis = jedis()) {
            String key = toLastMinuteRequestsKey(jedis, executorName, teamId, methodName);
            List<String> values = jedis.lrange(key, 0, jedis.llen(key) - 1);
            LastMinuteRequests requests = new LastMinuteRequests();
            requests.addAll(values.stream().map(Long::valueOf).collect(toList()));
            return requests;
        }
    }

    @Override
    public void addToWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        try (Jedis jedis = jedis()) {
            String key = toWaitingMessageIdsKey(jedis, executorName, teamId, methodName);
            jedis.rpush(key, messageId);
        }
    }

    @Override
    public void deleteFromWaitingMessageIds(String executorName, String teamId, String methodName, String messageId) {
        try (Jedis jedis = jedis()) {
            String key = toWaitingMessageIdsKey(jedis, executorName, teamId, methodName);
            jedis.lrem(key, 1, messageId);
        }
    }

    @Slf4j
    public static class MaintenanceJob implements Runnable {
        private final BaseRedisMetricsDatastore store;
        private long lastExecutionTimestampMillis;

        public MaintenanceJob(BaseRedisMetricsDatastore store) {
            this.store = store;
        }

        // Determine the worker

        @Override
        public void run() {
            Long startMillis = null;
            if (this.store.isTraceMode()) {
                startMillis = System.currentTimeMillis();
            }

            Map<String, Map<String, RequestStats>> allStats = store.getAllStats();
            for (Map.Entry<String, Map<String, RequestStats>> executor : allStats.entrySet()) {
                String executorName = executor.getKey();

                if (this.store.isTraceMode()) {
                    log.debug("Going to maintain {} metrics (executor: {}, teams: {})",
                            this.store.getMetricsType(),
                            executorName,
                            executor.getValue().keySet().size()
                    );
                }
                for (Map.Entry<String, RequestStats> team : executor.getValue().entrySet()) {
                    String teamId = team.getKey();
                    RequestStats stats = team.getValue();
                    if (stats == null) {
                        continue;
                    }
                    if (stats.getLastRequestTimestampMillis() != null
                            && stats.getLastRequestTimestampMillis() <= this.lastExecutionTimestampMillis) {
                        if (this.store.isTraceMode()) {
                            log.debug("No request for team: {} since the last maintenance", teamId);
                        }
                        continue;
                    }
                    if (this.store.isTraceMode()) {
                        log.debug("Going to maintain the data for team: {}", teamId);
                    }
                    // Last Minute Requests
                    for (String methodName : stats.getLastMinuteRequests().keySet()) {
                        store.updateNumberOfLastMinuteRequests(executorName, teamId, methodName);
                    }
                    // Current Queue Size
                    for (String methodName : stats.getCurrentQueueSize().keySet()) {
                        store.updateCurrentQueueSize(executorName, teamId, methodName);
                    }
                    // Remove rate limited methods if already recovered
                    List<String> methodNamesToRemove = new ArrayList<>();
                    for (Map.Entry<String, Long> each : stats.getRateLimitedMethods().entrySet()) {
                        String methodName = each.getKey();
                        Long millisToRetry = each.getValue();
                        long nowMillis = System.currentTimeMillis();
                        if (millisToRetry < nowMillis) {
                            methodNamesToRemove.add(methodName);
                        }
                    }
                    for (String methodName : methodNamesToRemove) {
                        stats.getRateLimitedMethods().remove(methodName);
                    }
                }
            }

            this.lastExecutionTimestampMillis = System.currentTimeMillis();

            if (this.store.isTraceMode()) {
                long spentMillis = System.currentTimeMillis() - startMillis;
                log.debug("{} metrics maintenance completed ({} ms)", this.store.getMetricsType(), spentMillis);
            }
        }
    }
}

package com.slack.api.rate_limits.metrics;

import com.slack.api.util.thread.ExecutorServiceProvider;

import java.util.Map;

public interface MetricsDatastore {

    String DEFAULT_SINGLETON_EXECUTOR_NAME = "DEFAULT_SINGLETON_EXECUTOR";

    default int getNumberOfNodes() {
        return 1;
    }

    Map<String, Map<String, RequestStats>> getAllStats();

    default RequestStats getStats(String teamId) {
        return getStats(DEFAULT_SINGLETON_EXECUTOR_NAME, teamId);
    }

    RequestStats getStats(String executorName, String teamId);

    void incrementAllCompletedCalls(String executorName, String teamId, String methodName);

    void incrementSuccessfulCalls(String executorName, String teamId, String methodName);

    void incrementUnsuccessfulCalls(String executorName, String teamId, String methodName);

    void incrementFailedCalls(String executorName, String teamId, String methodName);

    void updateCurrentQueueSize(String executorName, String teamId, String methodName);

    void setCurrentQueueSize(String executorName, String teamId, String methodName, Integer value);

    void updateNumberOfLastMinuteRequests(String executorName, String teamId, String methodName);

    Integer getNumberOfLastMinuteRequests(String executorName, String teamId, String methodName);

    void setNumberOfLastMinuteRequests(String executorName, String teamId, String methodName, Integer value);

    Long getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName);

    void setRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String methodName, Long epochTimeMillis);

    void addToLastMinuteRequests(String executorName, String teamId, String methodName, Long currentMillis);

    LastMinuteRequests getLastMinuteRequests(String executorName, String teamId, String methodName);

    void addToWaitingMessageIds(String executorName, String teamId, String methodName, String messageId);

    void deleteFromWaitingMessageIds(String executorName, String teamId, String methodName, String messageId);

    ExecutorServiceProvider getExecutorServiceProvider();

    void setExecutorServiceProvider(ExecutorServiceProvider executorServiceProvider);

    boolean isTraceMode();

    void setTraceMode(boolean traceMode);

    boolean isStatsEnabled();

    void setStatsEnabled(boolean statsEnabled);

    long getRateLimiterBackgroundJobIntervalMillis();

    void setRateLimiterBackgroundJobIntervalMillis(long rateLimiterBackgroundJobIntervalMillis);

}

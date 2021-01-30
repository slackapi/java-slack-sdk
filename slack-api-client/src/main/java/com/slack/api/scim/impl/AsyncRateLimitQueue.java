package com.slack.api.scim.impl;

import com.slack.api.rate_limits.WaitTime;
import com.slack.api.rate_limits.queue.QueueMessage;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.SCIMConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class AsyncRateLimitQueue extends RateLimitQueue<
        AsyncExecutionSupplier<? extends SCIMApiResponse>,
        AsyncRateLimitQueue.SCIMMessage> {

    @Data
    @AllArgsConstructor
    public static class SCIMMessage extends QueueMessage<AsyncExecutionSupplier> {
        private String id;
        private long millisToRun;
        private WaitTime waitTime;
        private AsyncExecutionSupplier<?> supplier;
    }

    // Executor name -> Team ID -> Queue
    private static final ConcurrentMap<String, ConcurrentMap<String, AsyncRateLimitQueue>> ALL_QUEUES = new ConcurrentHashMap<>();

    private static ConcurrentMap<String, AsyncRateLimitQueue> getInstance(String executorName) {
        ConcurrentMap<String, AsyncRateLimitQueue> teamIdToQueue = ALL_QUEUES.get(executorName);
        if (teamIdToQueue == null) {
            teamIdToQueue = new ConcurrentHashMap<>();
            ALL_QUEUES.put(executorName, teamIdToQueue);
        }
        return teamIdToQueue;
    }

    private AsyncSCIMRateLimiter rateLimiter; // intentionally mutable

    public AsyncSCIMRateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(AsyncSCIMRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    private final ConcurrentMap<String, LinkedBlockingQueue<SCIMMessage>> methodNameToActiveQueue = new ConcurrentHashMap<>();

    private AsyncRateLimitQueue(SCIMConfig config) {
        this.rateLimiter = new AsyncSCIMRateLimiter(config);
    }

    public static AsyncRateLimitQueue get(String executorName, String teamId) {
        if (executorName == null || teamId == null) {
            throw new IllegalArgumentException("`executorName` and `teamId` are required");
        }
        ConcurrentMap<String, AsyncRateLimitQueue> teamIdToQueue = getInstance(executorName);
        return teamIdToQueue.get(teamId);
    }

    public static AsyncRateLimitQueue getOrCreate(SCIMConfig config, String teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("`teamId` is required");
        }
        ConcurrentMap<String, AsyncRateLimitQueue> teamIdToQueue = getInstance(config.getExecutorName());
        AsyncRateLimitQueue queue = teamIdToQueue.get(teamId);
        if (queue != null && queue.getRateLimiter().getMetricsDatastore() != config.getMetricsDatastore()) {
            // As the metrics datastore has been changed, we should replace the executor
            queue.setRateLimiter(new AsyncSCIMRateLimiter(config));
        }
        if (queue == null) {
            queue = new AsyncRateLimitQueue(config);
            teamIdToQueue.put(teamId, queue);
        }
        return queue;
    }

    @Override
    protected SCIMMessage buildNewMessage(
            String messageId,
            long epochMillisToRun,
            WaitTime waitTime,
            AsyncExecutionSupplier<? extends SCIMApiResponse> methodsSupplier
    ) {
        return new SCIMMessage(messageId, epochMillisToRun, waitTime, methodsSupplier);
    }

}

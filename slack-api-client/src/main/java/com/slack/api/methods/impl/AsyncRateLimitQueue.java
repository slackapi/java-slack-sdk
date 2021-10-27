package com.slack.api.methods.impl;

import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.SlackApiResponse;
import com.slack.api.rate_limits.WaitTime;
import com.slack.api.rate_limits.queue.QueueMessage;
import com.slack.api.rate_limits.queue.RateLimitQueue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class AsyncRateLimitQueue extends RateLimitQueue<
        AsyncExecutionSupplier<? extends SlackApiResponse>,
        AsyncRateLimitQueue.Message> {

    // Executor name -> Team ID -> Queue
    private static final ConcurrentMap<String, ConcurrentMap<String, AsyncRateLimitQueue>> ALL_QUEUES = new ConcurrentHashMap<>();

    private static ConcurrentMap<String, AsyncRateLimitQueue> getInstance(String executorName) {
        return ALL_QUEUES.computeIfAbsent(executorName, key -> new ConcurrentHashMap<>());
    }

    private AsyncMethodsRateLimiter rateLimiter; // intentionally mutable

    private AsyncRateLimitQueue(MethodsConfig config) {
        this.rateLimiter = new AsyncMethodsRateLimiter(config);
    }

    public void setRateLimiter(AsyncMethodsRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public static AsyncRateLimitQueue get(String executorName, String teamId) {
        if (executorName == null || teamId == null) {
            throw new IllegalArgumentException("`executorName` and `teamId` are required");
        }
        ConcurrentMap<String, AsyncRateLimitQueue> teamIdToQueue = getInstance(executorName);
        return teamIdToQueue.get(teamId);
    }

    public static AsyncRateLimitQueue getOrCreate(MethodsConfig config, String teamId) {
        if (teamId == null) {
            throw new IllegalArgumentException("`teamId` is required");
        }

        ConcurrentMap<String, AsyncRateLimitQueue> teamIdToQueue = getInstance(config.getExecutorName());

        teamIdToQueue.computeIfPresent(teamId, (key, value) -> {
            if (value.getRateLimiter().getMetricsDatastore() != config.getMetricsDatastore()) {
                value.setRateLimiter(new AsyncMethodsRateLimiter(config));
            }

            return value;
        });

        return teamIdToQueue.computeIfAbsent(teamId, key -> new AsyncRateLimitQueue(config));
    }

    @Data
    @AllArgsConstructor
    public static class Message extends QueueMessage<AsyncExecutionSupplier<? extends SlackApiResponse>> {
        private String id;
        private long millisToRun;
        private WaitTime waitTime;
        private AsyncExecutionSupplier<?> supplier;
    }

    @Override
    protected AsyncMethodsRateLimiter getRateLimiter() {
        return this.rateLimiter;
    }

    @Override
    protected Message buildNewMessage(String messageId, long epochMillisToRun, WaitTime waitTime, AsyncExecutionSupplier<? extends SlackApiResponse> methodsSupplier) {
        return new Message(messageId, epochMillisToRun, waitTime, methodsSupplier);
    }

}

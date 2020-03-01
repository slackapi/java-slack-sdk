package com.slack.api.methods.impl;

import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.SlackApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class AsyncRateLimitQueue {

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

    private AsyncMethodsRateLimiter rateLimiter; // intentionally mutable

    public AsyncMethodsRateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(AsyncMethodsRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    private final ConcurrentMap<String, LinkedBlockingQueue<Message>> methodNameToActiveQueue = new ConcurrentHashMap<>();

    private AsyncRateLimitQueue(MethodsConfig config) {
        this.rateLimiter = new AsyncMethodsRateLimiter(config);
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
        AsyncRateLimitQueue queue = teamIdToQueue.get(teamId);
        if (queue != null && queue.getRateLimiter().getMetricsDatastore() != config.getMetricsDatastore()) {
            // As the metrics datastore has been changed, we should replace the executor
            queue.setRateLimiter(new AsyncMethodsRateLimiter(config));
        }
        if (queue == null) {
            queue = new AsyncRateLimitQueue(config);
            teamIdToQueue.put(teamId, queue);
        }
        return queue;
    }

    @Data
    @AllArgsConstructor
    private static class Message {
        private String id;
        private long millisToRun;
        private AsyncMethodsRateLimiter.WaitTime waitTime;
        private AsyncExecutionSupplier<?> supplier;
    }

    private LinkedBlockingQueue<Message> getOrCreateActiveQueue(String methodName) {
        LinkedBlockingQueue<Message> queue = methodNameToActiveQueue.get(methodName);
        if (queue != null) {
            return queue;
        } else {
            LinkedBlockingQueue<Message> newQueue = new LinkedBlockingQueue<>();
            methodNameToActiveQueue.putIfAbsent(methodName, newQueue);
            return newQueue;
        }
    }

    public <T extends SlackApiResponse> void enqueue(
            String messageId,
            String teamId,
            String methodName,
            Map<String, String> params,
            AsyncExecutionSupplier<T> methodsSupplier) throws InterruptedException {

        AsyncMethodsRateLimiter.WaitTime waitTime;
        if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
            waitTime = rateLimiter.acquireWaitTimeForChatPostMessage(teamId, params.get("channel"));
        } else {
            waitTime = rateLimiter.acquireWaitTime(teamId, methodName);
        }

        LinkedBlockingQueue<Message> activeQueue = getOrCreateActiveQueue(methodName);
        long epochMillisToRun = System.currentTimeMillis() + waitTime.getMillisToWait();
        Message message = new Message(messageId, epochMillisToRun, waitTime, methodsSupplier);
        activeQueue.put(message);

        if (log.isDebugEnabled()) {
            log.debug("A new message has been enqueued (id: {}, pace: {}, wait time: {})",
                    message.getId(),
                    message.getWaitTime().getPace(),
                    message.getWaitTime().getMillisToWait()
            );
        }
    }

    public synchronized void remove(String methodName, String messageId) {
        LinkedBlockingQueue<Message> activeQueue = getOrCreateActiveQueue(methodName);
        Message toRemove = null;
        for (Message message : activeQueue) {
            if (message.getId().equals(messageId)) {
                toRemove = message;
                break;
            }
        }
        activeQueue.remove(toRemove);
    }

    public synchronized <T extends SlackApiResponse> AsyncExecutionSupplier<T> dequeueIfReady(
            String messageId,
            String teamId,
            String methodName,
            Map<String, String> params) {
        LinkedBlockingQueue<Message> activeQueue = getOrCreateActiveQueue(methodName);
        Message message = activeQueue.peek();
        if (message == null) {
            throw new IllegalStateException("No message is found in the queue");
        }
        if (message.getId().equals(messageId)
                && message.getMillisToRun() <= System.currentTimeMillis()) {
            // Make sure if the situation is still the same with the timing we determined the wait time
            AsyncMethodsRateLimiter.WaitTime original = message.getWaitTime();
            AsyncMethodsRateLimiter.WaitTime latest;
            if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
                latest = rateLimiter.acquireWaitTimeForChatPostMessage(teamId, params.get("channel"));
            } else {
                latest = rateLimiter.acquireWaitTime(teamId, methodName);
            }
            if (log.isDebugEnabled()) {
                log.debug("Latest: {} ({} millis), original: {} ({} millis)",
                        latest.getPace(), latest.getMillisToWait(), original.getPace(), original.getMillisToWait());
            }
            if (latest.getPace() != original.getPace() && latest.getMillisToWait() > original.getMillisToWait()) {
                // The latest situation is worse than that timing.
                long newMillisToRun = System.currentTimeMillis() + latest.getMillisToWait();
                message.setMillisToRun(newMillisToRun);
                message.setWaitTime(latest);
            } else {
                AsyncExecutionSupplier<T> supplier = (AsyncExecutionSupplier<T>) activeQueue.poll().getSupplier();
                return supplier;
            }
        }
        return null;
    }

    public Integer getCurrentActiveQueueSize(String methodNameWithSuffix) {
        LinkedBlockingQueue<Message> activeQueue = methodNameToActiveQueue.get(methodNameWithSuffix);
        return activeQueue != null ? activeQueue.size() : 0;
    }

}

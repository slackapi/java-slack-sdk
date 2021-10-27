package com.slack.api.rate_limits.queue;

import com.slack.api.methods.Methods;
import com.slack.api.rate_limits.RateLimiter;
import com.slack.api.rate_limits.WaitTime;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public abstract class RateLimitQueue<SUPPLIER, MSG extends QueueMessage> {

    protected abstract RateLimiter getRateLimiter();

    protected final ConcurrentMap<String, LinkedBlockingQueue<MSG>> methodNameToActiveQueue = new ConcurrentHashMap<>();

    protected LinkedBlockingQueue<MSG> getOrCreateActiveQueue(String methodName) {
        return methodNameToActiveQueue.computeIfAbsent(methodName, key -> new LinkedBlockingQueue<>());
    }

    public synchronized SUPPLIER dequeueIfReady(
            String messageId,
            String teamId,
            String methodName,
            Map<String, String> params) {
        LinkedBlockingQueue<MSG> activeQueue = getOrCreateActiveQueue(methodName);
        MSG message = activeQueue.peek();
        if (message == null) {
            throw new IllegalStateException("No message is found in the queue");
        }
        if (message.getId().equals(messageId)
                && message.getMillisToRun() <= System.currentTimeMillis()) {
            // Make sure if the situation is still the same with the timing we determined the wait time
            WaitTime original = message.getWaitTime();
            WaitTime latest;
            if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
                latest = getRateLimiter().acquireWaitTimeForChatPostMessage(teamId, params.get("channel"));
            } else {
                latest = getRateLimiter().acquireWaitTime(teamId, methodName);
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
                SUPPLIER supplier = (SUPPLIER) activeQueue.poll().getSupplier();
                return supplier;
            }
        }
        return null;
    }

    protected abstract MSG buildNewMessage(
            String messageId,
            long epochMillisToRun,
            WaitTime waitTime,
            SUPPLIER methodsSupplier
    );

    public void enqueue(
            String messageId,
            String teamId,
            String methodName,
            Map<String, String> params,
            SUPPLIER methodsSupplier) throws InterruptedException {

        WaitTime waitTime = getRateLimiter().acquireWaitTime(teamId, methodName);
        if (methodName.equals(Methods.CHAT_POST_MESSAGE)) {
            waitTime = getRateLimiter().acquireWaitTimeForChatPostMessage(teamId, params.get("channel"));
        }
        LinkedBlockingQueue<MSG> activeQueue = getOrCreateActiveQueue(methodName);
        long epochMillisToRun = System.currentTimeMillis() + waitTime.getMillisToWait();
        MSG message = buildNewMessage(messageId, epochMillisToRun, waitTime, methodsSupplier);
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
        LinkedBlockingQueue<MSG> activeQueue = getOrCreateActiveQueue(methodName);
        MSG toRemove = null;
        for (MSG message : activeQueue) {
            if (message.getId().equals(messageId)) {
                toRemove = message;
                break;
            }
        }
        activeQueue.remove(toRemove);
    }

    public Integer getCurrentActiveQueueSize(String methodNameWithSuffix) {
        LinkedBlockingQueue<MSG> activeQueue = methodNameToActiveQueue.get(methodNameWithSuffix);
        return activeQueue != null ? activeQueue.size() : 0;
    }

}

package com.slack.api.rate_limits.metrics;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class LiveRequestStats {
    private final ConcurrentMap<String, AtomicLong> allCompletedCalls = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> successfulCalls = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> unsuccessfulCalls = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> failedCalls = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> currentQueueSize = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> lastMinuteRequests = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> rateLimitedMethods = new ConcurrentHashMap<>();
}

package com.slack.api.methods.impl;

import com.slack.api.methods.*;
import com.slack.api.methods.metrics.LastMinuteRequests;
import com.slack.api.methods.metrics.MetricsDatastore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncMethodsRateLimiter {

    private final MethodsConfig config;
    private final MetricsDatastore metricsDatastore;

    public AsyncMethodsRateLimiter(MethodsConfig config) {
        this.config = config;
        this.metricsDatastore = config.getMetricsDatastore();
    }

    @Data
    @AllArgsConstructor
    static class WaitTime {
        private long millisToWait;
        private Pace pace;
    }

    enum Pace {
        RateLimited,
        Safe,
        Optimal,
        TooFastPaced,
        Burst
    }

    public WaitTime acquireWaitTime(String teamId, String methodName) {
        return calculateWaitTime(teamId, methodName);
    }

    public WaitTime acquireWaitTimeForChatPostMessage(String teamId, String channelId) {
        return calculateWaitTime(teamId, Methods.CHAT_POST_MESSAGE, "_" + channelId);
    }

    private WaitTime calculateWaitTime(String teamId, String methodName) {
        return calculateWaitTime(teamId, methodName, "");
    }

    private WaitTime calculateWaitTime(String teamId, String methodName, String suffix) {
        String key = methodName + suffix;

        MethodsRateLimitTier tier = MethodsRateLimits.lookupRateLimitTier(methodName);
        int allowedRequests = getAllowedRequestsPerMinute(tier);
        if (log.isDebugEnabled()) {
            int currentRequests = getNumberOfLastMinuteRequests(teamId, key);
            log.debug("current requests: {}, allowed requests: {}", currentRequests, allowedRequests);
        }

        Long retryAfterEpochMillis = metricsDatastore.getRateLimitedMethodRetryEpochMillis(
                config.getExecutorName(), teamId, methodName);
        if (retryAfterEpochMillis != null) { // rate limited now
            long waitMillis = retryAfterEpochMillis - System.currentTimeMillis();
            WaitTime currentSituation = calculateWaitTime(teamId, key, allowedRequests);
            long additionalMillis = currentSituation.getMillisToWait();
            if (currentSituation.getPace() == Pace.Burst) {
                additionalMillis = additionalMillis * 7;
            } else if (currentSituation.getPace() == Pace.TooFastPaced) {
                additionalMillis = additionalMillis * 5;
            }
            return new WaitTime(waitMillis + additionalMillis, Pace.RateLimited);
        } else {
            return calculateWaitTime(teamId, key, allowedRequests);
        }
    }

    private WaitTime calculateWaitTime(String teamId, String key, int allowedRequests) {
        LastMinuteRequests lastMinuteRequests = metricsDatastore.getLastMinuteRequests(config.getExecutorName(), teamId, key);
        if (isBurst(lastMinuteRequests, allowedRequests)) {
            if (log.isDebugEnabled()) {
                log.debug("Burst requests detected (method: {}, last minute requests: {}, allowed: {})",
                        key, lastMinuteRequests.size(), allowedRequests);
            }
            Double waitMillis = 180000D / allowedRequests; // change the pace (60 -> 180 seconds)
            return new WaitTime(waitMillis.longValue(), Pace.Burst);
        } else if (isTooFastPaced(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 120000D / allowedRequests; // change the pace (60 -> 120 seconds)
            return new WaitTime(waitMillis.longValue(), Pace.TooFastPaced);
        } else if (isOptimalPace(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 60000D / allowedRequests; // keep the pace
            return new WaitTime(waitMillis.longValue(), Pace.Optimal);
        } else if (isSomewhatBusy(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 30000D / allowedRequests; // let it a bit slower
            return new WaitTime(waitMillis.longValue(), Pace.Safe);
        } else {
            return new WaitTime(0, Pace.Safe);
        }
    }

    private boolean isBurst(LastMinuteRequests lastMinuteRequests, int allowedRequests) {
        if (lastMinuteRequests.size() > (allowedRequests / 10)) {
            long threeSecondsAgo = System.currentTimeMillis() - 3000L;
            long burstRequests = lastMinuteRequests.stream().filter(millis -> millis > threeSecondsAgo).count();
            return burstRequests >= (allowedRequests / 10);
        }
        return false;
    }

    private static boolean isSomewhatBusy(LastMinuteRequests lastMinuteRequests, int allowedRequests) {
        int currentSize = lastMinuteRequests.size();
        return currentSize >= allowedRequests * 0.3 && currentSize < allowedRequests * 0.6;
    }

    private static boolean isOptimalPace(LastMinuteRequests lastMinuteRequests, int allowedRequests) {
        int currentSize = lastMinuteRequests.size();
        return currentSize >= allowedRequests * 0.6 && currentSize < allowedRequests * 0.9;
    }

    private static boolean isTooFastPaced(LastMinuteRequests lastMinuteRequests, int allowedRequests) {
        return lastMinuteRequests.size() >= allowedRequests * 0.9;
    }

    private Integer getAllowedRequestsPerMinute(MethodsRateLimitTier tier) {
        Integer allowedRequestsForOneNode = MethodsRateLimitTier.getAllowedRequestsPerMinute(tier);
        return allowedRequestsForOneNode / metricsDatastore.getNumberOfNodes();
    }

    private Integer getNumberOfLastMinuteRequests(String teamId, String methodNameWithSuffix) {
        return metricsDatastore.getNumberOfLastMinuteRequests(
                config.getExecutorName(),
                teamId,
                methodNameWithSuffix);
    }

}

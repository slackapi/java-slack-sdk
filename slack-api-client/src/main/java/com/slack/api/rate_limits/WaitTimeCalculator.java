package com.slack.api.rate_limits;

import com.slack.api.methods.Methods;
import com.slack.api.methods.MethodsRateLimitTier;
import com.slack.api.rate_limits.metrics.LastMinuteRequests;
import com.slack.api.rate_limits.metrics.RequestPace;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.slack.api.methods.MethodsRateLimitTier.SpecialTier_chat_postMessage;

@Slf4j
public abstract class WaitTimeCalculator {

    public abstract Integer getNumberOfNodes();

    public abstract String getExecutorName();

    public abstract Optional<Long> getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String key);

    public abstract LastMinuteRequests getLastMinuteRequests(String executorName, String teamId, String key);

    public WaitTime calculateWaitTime(String teamId, String key, int allowedRequests) {
        LastMinuteRequests lastMinuteRequests = getLastMinuteRequests(getExecutorName(), teamId, key);
        if (isBurst(lastMinuteRequests, allowedRequests)) {
            if (log.isDebugEnabled()) {
                log.debug("Burst requests detected (method: {}, last minute requests: {}, allowed: {})",
                        key, lastMinuteRequests.size(), allowedRequests);
            }
            Double waitMillis = 180000D / allowedRequests; // change the pace (60 -> 180 seconds)
            return new WaitTime(waitMillis.longValue(), RequestPace.Burst);
        } else if (isTooFastPaced(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 120000D / allowedRequests; // change the pace (60 -> 120 seconds)
            return new WaitTime(waitMillis.longValue(), RequestPace.TooFastPaced);
        } else if (isOptimalPace(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 60000D / allowedRequests; // keep the pace
            return new WaitTime(waitMillis.longValue(), RequestPace.Optimal);
        } else if (isSomewhatBusy(lastMinuteRequests, allowedRequests)) {
            Double waitMillis = 30000D / allowedRequests; // let it a bit slower
            return new WaitTime(waitMillis.longValue(), RequestPace.Safe);
        } else {
            return new WaitTime(0, RequestPace.Safe);
        }
    }

    /**
     * @deprecated Use #calculateWaitTimeForChatPostMessage(String, String, int) instead
     */
    @Deprecated
    public WaitTime calculateWaitTimeForChatPostMessage(String teamId, String channel) {
        return calculateWaitTimeForChatPostMessage(teamId, channel, getAllowedRequestsPerMinute(SpecialTier_chat_postMessage));
    }

    public WaitTime calculateWaitTimeForChatPostMessage(String teamId, String channel, int allowedRequests) {
        return calculateWaitTime(
                teamId,
                Methods.CHAT_POST_MESSAGE + "_" + channel,
                allowedRequests
        );
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

    public Integer getAllowedRequestsPerMinute(MethodsRateLimitTier tier) {
        Integer allowedRequestsPerMinute = MethodsRateLimitTier.getAllowedRequestsPerMinute(tier);
        return allowedRequestsPerMinute / getNumberOfNodes();
    }

}

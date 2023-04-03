package com.slack.api.methods.impl;

import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.MethodsCustomRateLimitResolver;
import com.slack.api.methods.MethodsRateLimits;
import com.slack.api.rate_limits.RateLimiter;
import com.slack.api.rate_limits.WaitTime;
import com.slack.api.rate_limits.WaitTimeCalculator;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.slack.api.methods.MethodsRateLimitTier.SpecialTier_chat_postMessage;

@Slf4j
public class AsyncMethodsRateLimiter implements RateLimiter {

    private final MetricsDatastore metricsDatastore;
    private final MethodsCustomRateLimitResolver customRateLimitResolver;
    private final WaitTimeCalculator waitTimeCalculator;

    public MetricsDatastore getMetricsDatastore() {
        return metricsDatastore;
    }

    public AsyncMethodsRateLimiter(MethodsConfig config) {
        this.metricsDatastore = config.getMetricsDatastore();
        this.customRateLimitResolver = config.getCustomRateLimitResolver();
        this.waitTimeCalculator = new MethodsWaitTimeCalculator(config);
    }

    @Override
    public WaitTime acquireWaitTime(String teamId, String methodName) {
        return waitTimeCalculator.calculateWaitTime(
                teamId,
                methodName,
                getAllowedRequestsPerMinute(teamId, methodName)
        );
    }

    public int getAllowedRequestsPerMinute(String teamId, String methodName) {
        Optional<Integer> custom = customRateLimitResolver.getCustomAllowedRequestsPerMinute(teamId, methodName);
        if (custom.isPresent()) {
            return custom.get();
        }
        return waitTimeCalculator.getAllowedRequestsPerMinute(MethodsRateLimits.lookupRateLimitTier(methodName));
    }

    public int getAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel) {
        Optional<Integer> custom = customRateLimitResolver.getCustomAllowedRequestsForChatPostMessagePerMinute(teamId, channel);
        if (custom.isPresent()) {
            return custom.get();
        }
        return waitTimeCalculator.getAllowedRequestsPerMinute(SpecialTier_chat_postMessage);
    }

    @Override
    public WaitTime acquireWaitTimeForChatPostMessage(String teamId, String channel) {
        return waitTimeCalculator.calculateWaitTimeForChatPostMessage(
                teamId,
                channel,
                getAllowedRequestsForChatPostMessagePerMinute(teamId, channel)
        );
    }

    public static class MethodsWaitTimeCalculator extends WaitTimeCalculator {
        private final MethodsConfig config;

        public MethodsWaitTimeCalculator(MethodsConfig config) {
            this.config = config;
        }

        @Override
        public Optional<Long> getRateLimitedMethodRetryEpochMillis(String executorName, String teamId, String key) {
            return Optional.ofNullable(config.getMetricsDatastore().getRateLimitedMethodRetryEpochMillis(
                    executorName, teamId, key
            ));
        }

        @Override
        public Integer getNumberOfNodes() {
            return config.getMetricsDatastore().getNumberOfNodes();
        }

        @Override
        public String getExecutorName() {
            return config.getExecutorName();
        }

        @Override
        public com.slack.api.rate_limits.metrics.LastMinuteRequests getLastMinuteRequests(
                String executorName, String teamId, String key) {
            return config.getMetricsDatastore().getLastMinuteRequests(executorName, teamId, key);
        }
    }
}

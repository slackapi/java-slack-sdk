package com.slack.api.scim.impl;

import com.slack.api.rate_limits.RateLimiter;
import com.slack.api.rate_limits.WaitTime;
import com.slack.api.rate_limits.WaitTimeCalculator;
import com.slack.api.rate_limits.metrics.LastMinuteRequests;
import com.slack.api.rate_limits.metrics.MetricsDatastore;
import com.slack.api.rate_limits.metrics.RequestPace;
import com.slack.api.rate_limits.metrics.RequestStats;
import com.slack.api.scim.SCIMConfig;
import com.slack.api.scim.SCIMEndpointName;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import static com.slack.api.scim.SCIMEndpointName.*;

@Slf4j
public class AsyncSCIMRateLimiter implements RateLimiter {

    private final MetricsDatastore metricsDatastore;
    private final String executorName;
    private final SCIMWaitTimeCalculator waitTimeCalculator;

    public MetricsDatastore getMetricsDatastore() {
        return metricsDatastore;
    }

    public AsyncSCIMRateLimiter(SCIMConfig config) {
        this.metricsDatastore = config.getMetricsDatastore();
        this.executorName = config.getExecutorName();
        this.waitTimeCalculator = new SCIMWaitTimeCalculator(config);
    }

    public static class SCIMWaitTimeCalculator extends WaitTimeCalculator {
        private final SCIMConfig config;

        public SCIMWaitTimeCalculator(SCIMConfig config) {
            this.config = config;
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
        public Optional<Long> getRateLimitedMethodRetryEpochMillis(
                String executorName, String teamId, String key) {
            return Optional.ofNullable(config.getMetricsDatastore().getRateLimitedMethodRetryEpochMillis(
                    executorName, teamId, key
            ));
        }

        @Override
        public LastMinuteRequests getLastMinuteRequests(
                String executorName, String teamId, String key) {
            return config.getMetricsDatastore().getLastMinuteRequests(executorName, teamId, key);
        }
    }

    public int getAllowedRequestsPerMinutes(SCIMEndpointName endpoint) {
        switch (endpoint) {
            case getServiceProviderConfigs:
            case searchUsers:
            case searchGroups:
                return 1000; // the maximum (the org-wide limits will be applied later)
            case readUser:
            case readGroup:
                return 300;
            case createUser:
            case patchUser:
            case updateUser:
            case deleteUser:
            case createGroup:
            case patchGroup:
            case updateGroup:
            case deleteGroup:
                return 180;
            default:
                break;
        }
        return 180; // the most conservative value
    }

    public int getRemainingAllowedRequestsPerMinutesForOrg(SCIMEndpointName endpoint, RequestStats stats) {
        Map<String, Integer> r = stats.getLastMinuteRequests();
        switch (endpoint) {
            case getServiceProviderConfigs:
            case searchUsers:
            case searchGroups:
            case readUser:
            case readGroup:
                return 1000 - (Optional.ofNullable(r.get(getServiceProviderConfigs.name())).orElse(0)
                        + Optional.ofNullable(r.get(searchUsers.name())).orElse(0)
                        + Optional.ofNullable(r.get(searchGroups.name())).orElse(0)
                        + Optional.ofNullable(r.get(readUser.name())).orElse(0)
                        + Optional.ofNullable(r.get(readGroup.name())).orElse(0)
                );
            case createUser:
            case patchUser:
            case updateUser:
            case deleteUser:
            case createGroup:
            case patchGroup:
            case updateGroup:
            case deleteGroup:
            default:
                return 600 - (Optional.ofNullable(r.get(createUser.name())).orElse(0)
                        + Optional.ofNullable(r.get(patchUser.name())).orElse(0)
                        + Optional.ofNullable(r.get(updateUser.name())).orElse(0)
                        + Optional.ofNullable(r.get(deleteUser.name())).orElse(0)
                        + Optional.ofNullable(r.get(createGroup.name())).orElse(0)
                        + Optional.ofNullable(r.get(patchGroup.name())).orElse(0)
                        + Optional.ofNullable(r.get(updateGroup.name())).orElse(0)
                        + Optional.ofNullable(r.get(deleteGroup.name())).orElse(0)
                );
        }
    }

    @Override
    public WaitTime acquireWaitTime(String teamId, String methodName) {
        Optional<Long> rateLimitedEpochMillis = waitTimeCalculator
                .getRateLimitedMethodRetryEpochMillis(executorName, teamId, methodName);
        if (rateLimitedEpochMillis.isPresent()) {
            long millisToWait = rateLimitedEpochMillis.get() - System.currentTimeMillis();
            return new WaitTime(millisToWait, RequestPace.RateLimited);
        }
        SCIMEndpointName endpoint = SCIMEndpointName.valueOf(methodName);
        int orgRemainingRequests = getRemainingAllowedRequestsPerMinutesForOrg(
                endpoint, metricsDatastore.getStats(this.executorName, teamId));
        int endpointAllowedRequests = getAllowedRequestsPerMinutes(endpoint);
        int allowedRequests = endpointAllowedRequests > orgRemainingRequests
                ? endpointAllowedRequests : orgRemainingRequests;
        return waitTimeCalculator.calculateWaitTime(teamId, methodName, allowedRequests);
    }

    @Override
    public WaitTime acquireWaitTimeForChatPostMessage(String teamId, String channel) {
        throw new IllegalStateException("This rate limiter does not handle the pattern");
    }

    @Override
    public WaitTime acquireWaitTimeForAssistantThreadsSetStatus(String teamId, String channel) {
        throw new IllegalStateException("This rate limiter does not handle the pattern");
    }

}

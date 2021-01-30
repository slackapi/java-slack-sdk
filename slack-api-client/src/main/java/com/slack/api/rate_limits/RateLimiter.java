package com.slack.api.rate_limits;

public interface RateLimiter {

    WaitTime acquireWaitTime(String teamId, String methodName);

    WaitTime acquireWaitTimeForChatPostMessage(String teamId, String channel);

}

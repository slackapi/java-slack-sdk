package com.slack.api.rate_limits;

public interface RateLimiter {

    WaitTime acquireWaitTime(String teamId, String methodName);

    WaitTime acquireWaitTimeForChatPostMessage(String teamId, String channel);

    WaitTime acquireWaitTimeForAssistantThreadsSetStatus(String teamId, String channel);

    long DEFAULT_BACKGROUND_JOB_INTERVAL_MILLIS = 1_000L;

}

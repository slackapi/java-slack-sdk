package com.slack.api.rate_limits.metrics;

public enum RequestPace {
    RateLimited,
    Safe,
    Optimal,
    TooFastPaced,
    Burst
}

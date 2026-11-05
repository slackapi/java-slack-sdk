package com.slack.api.util.http;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryAfterUtil {

    private RetryAfterUtil() {
    }

    /**
     * Parses a Retry-After header value as a number of seconds.
     *
     * @param retryAfter the raw header value, which can be null
     * @return the number of seconds, or null if the header is missing or is not an integer
     */
    public static Long parseSeconds(String retryAfter) {
        if (retryAfter == null) {
            return null;
        }
        try {
            return Long.valueOf(retryAfter);
        } catch (NumberFormatException e) {
            log.warn("Ignoring a non-numeric Retry-After header value: {}", retryAfter);
            return null;
        }
    }
}

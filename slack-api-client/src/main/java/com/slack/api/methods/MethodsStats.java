package com.slack.api.methods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodsStats {

    /**
     * Method name -> # of calls
     */
    @Builder.Default
    private final Map<String, Long> allCompletedCalls = new HashMap<>();
    /**
     * Method name -> # of calls
     */
    @Builder.Default
    private final Map<String, Long> successfulCalls = new HashMap<>();
    /**
     * Method name -> # of calls
     */
    @Builder.Default
    private final Map<String, Long> unsuccessfulCalls = new HashMap<>();
    /**
     * Method name -> # of calls
     */
    @Builder.Default
    private final Map<String, Long> failedCalls = new HashMap<>();
    /**
     * Method name -> The queue size
     */
    @Builder.Default
    private final Map<String, Integer> currentQueueSize = new HashMap<>();
    /**
     * Method name -> The number of the requests in the last minute
     */
    @Builder.Default
    private final Map<String, Integer> lastMinuteRequests = new HashMap<>();
    /**
     * Method name -> Epoch millis to retry
     */
    @Builder.Default
    private final Map<String, Long> rateLimitedMethods = new HashMap<>();

}

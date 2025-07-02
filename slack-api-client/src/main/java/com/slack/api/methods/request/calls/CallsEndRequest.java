package com.slack.api.methods.request.calls;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/calls.end
 */
@Data
@Builder
public class CallsEndRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * id returned when registering the call using the calls.add method
     */
    private String id;

    /**
     * Call duration in seconds
     */
    private Integer duration;

}
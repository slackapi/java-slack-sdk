package com.slack.api.methods.request.calls;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/calls.info
 */
@Data
@Builder
public class CallsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * id of the Call returned by the calls.add method.
     */
    private String id;
}
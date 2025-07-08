package com.slack.api.methods.request.functions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * https://docs.slack.dev/reference/methods/functions.completeError
 */
@Data
@Builder
public class FunctionsCompleteErrorRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    private String functionExecutionId;
    private String error;
}
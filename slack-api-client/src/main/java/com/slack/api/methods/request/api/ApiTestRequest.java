package com.slack.api.methods.request.api;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/api.test
 */
@Data
@Builder
public class ApiTestRequest implements SlackApiRequest {

    /**
     * example property to return
     */
    private String foo;

    /**
     * Error response to return
     */
    private String error;

    @Override
    public String getToken() {
        return null;
    }
}
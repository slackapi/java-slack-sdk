package com.slack.api.methods;

import java.util.List;
import java.util.Map;

public interface SlackApiResponse {

    /**
     * Returns all the HTTP response headers in the API response. The keys are lower-cased.
     */
    Map<String, List<String>> getHttpResponseHeaders();

    /**
     * Sets the response headers. Pass a Map object with lower-cased keys.
     */
    void setHttpResponseHeaders(Map<String, List<String>> headers);
}

package com.slack.api.bolt.handler;

import com.slack.api.bolt.context.WebEndpointContext;
import com.slack.api.bolt.request.WebEndpointRequest;
import com.slack.api.bolt.response.Response;

/**
 * A handler for additional web endpoints.
 */
@FunctionalInterface
public interface WebEndpointHandler {

    Response apply(WebEndpointRequest request, WebEndpointContext context);

}

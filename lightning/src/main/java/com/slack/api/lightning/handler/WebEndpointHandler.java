package com.slack.api.lightning.handler;

import com.slack.api.lightning.context.WebEndpointContext;
import com.slack.api.lightning.request.WebEndpointRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface WebEndpointHandler {

    Response apply(WebEndpointRequest req, WebEndpointContext context);

}

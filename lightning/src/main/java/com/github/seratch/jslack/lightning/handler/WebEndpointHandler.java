package com.github.seratch.jslack.lightning.handler;

import com.github.seratch.jslack.lightning.context.WebEndpointContext;
import com.github.seratch.jslack.lightning.request.WebEndpointRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface WebEndpointHandler {

    Response apply(WebEndpointRequest req, WebEndpointContext context);

}

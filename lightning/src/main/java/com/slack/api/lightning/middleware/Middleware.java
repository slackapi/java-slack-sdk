package com.slack.api.lightning.middleware;

import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface Middleware {

    Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception;

}

package com.slack.api.lightning.middleware;

import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;

/**
 * Middleware that handles requests from the Slack API server.
 */
@FunctionalInterface
public interface Middleware {

    Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception;

}

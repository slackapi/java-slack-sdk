package com.slack.api.bolt.middleware;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;

/**
 * Middleware that handles requests from the Slack API server.
 */
@FunctionalInterface
public interface Middleware {

    Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception;

}

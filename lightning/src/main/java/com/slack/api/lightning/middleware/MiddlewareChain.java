package com.slack.api.lightning.middleware;

import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface MiddlewareChain {

    Response next(Request req) throws Exception;

}

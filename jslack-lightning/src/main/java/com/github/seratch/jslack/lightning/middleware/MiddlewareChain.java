package com.github.seratch.jslack.lightning.middleware;

import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface MiddlewareChain {

    Response next(Request req) throws Exception;

}

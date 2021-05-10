package com.slack.api.bolt.handler;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface UnmatchedRequestHandler {

    Response handle(Request<?> request);

}

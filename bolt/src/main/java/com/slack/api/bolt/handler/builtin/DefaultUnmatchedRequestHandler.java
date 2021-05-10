package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.handler.UnmatchedRequestHandler;

public class DefaultUnmatchedRequestHandler implements UnmatchedRequestHandler {
    @Override
    public Response handle(Request<?> request) {
        return Response.json(404, "{\"error\":\"no handler found\"}");
    }
}

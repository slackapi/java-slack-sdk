package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.DefaultContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.ViewClosedRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface ViewClosedHandler extends Handler<DefaultContext, ViewClosedRequest, Response> {
}

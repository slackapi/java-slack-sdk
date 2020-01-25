package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.DefaultContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.ViewClosedRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface ViewClosedHandler extends Handler<DefaultContext, ViewClosedRequest, Response> {
}

package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.ActionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.MessageActionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface MessageActionHandler extends Handler<ActionContext, MessageActionRequest, Response> {
}

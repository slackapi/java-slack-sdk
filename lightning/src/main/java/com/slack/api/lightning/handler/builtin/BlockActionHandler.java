package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.ActionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.BlockActionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface BlockActionHandler extends Handler<ActionContext, BlockActionRequest, Response> {
}

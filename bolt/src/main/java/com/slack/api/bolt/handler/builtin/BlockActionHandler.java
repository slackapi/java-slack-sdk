package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface BlockActionHandler extends Handler<ActionContext, BlockActionRequest, Response> {
}

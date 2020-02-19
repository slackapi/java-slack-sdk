package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.MessageActionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.MessageActionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface MessageActionHandler extends Handler<MessageActionContext, MessageActionRequest, Response> {
}

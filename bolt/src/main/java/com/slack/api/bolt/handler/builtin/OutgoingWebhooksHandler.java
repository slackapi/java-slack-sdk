package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.OutgoingWebhooksContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.OutgoingWebhooksRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface OutgoingWebhooksHandler extends Handler<OutgoingWebhooksContext, OutgoingWebhooksRequest, Response> {
}

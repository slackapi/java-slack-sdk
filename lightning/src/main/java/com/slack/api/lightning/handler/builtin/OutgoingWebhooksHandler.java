package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.OutgoingWebhooksContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.OutgoingWebhooksRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface OutgoingWebhooksHandler extends Handler<OutgoingWebhooksContext, OutgoingWebhooksRequest, Response> {
}

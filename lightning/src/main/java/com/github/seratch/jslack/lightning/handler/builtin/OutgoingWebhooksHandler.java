package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.OutgoingWebhooksContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.OutgoingWebhooksRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface OutgoingWebhooksHandler extends Handler<OutgoingWebhooksContext, OutgoingWebhooksRequest, Response> {
}

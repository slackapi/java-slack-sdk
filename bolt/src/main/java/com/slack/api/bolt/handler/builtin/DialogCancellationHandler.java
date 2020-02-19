package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.DialogCancellationContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.DialogCancellationRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface DialogCancellationHandler extends Handler<DialogCancellationContext, DialogCancellationRequest, Response> {
}

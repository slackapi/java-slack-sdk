package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.DialogCancellationContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.DialogCancellationRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface DialogCancellationHandler extends Handler<DialogCancellationContext, DialogCancellationRequest, Response> {
}

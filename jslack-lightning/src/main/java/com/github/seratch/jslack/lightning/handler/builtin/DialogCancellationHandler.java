package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.DialogCancellationContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.DialogCancellationRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface DialogCancellationHandler extends Handler<DialogCancellationContext, DialogCancellationRequest, Response> {
}

package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.DialogSubmissionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.DialogSubmissionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface DialogSubmissionHandler extends Handler<DialogSubmissionContext, DialogSubmissionRequest, Response> {
}

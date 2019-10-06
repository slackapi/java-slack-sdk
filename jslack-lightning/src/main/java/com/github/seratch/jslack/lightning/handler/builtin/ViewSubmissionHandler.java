package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.ViewSubmissionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.ViewSubmissionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface ViewSubmissionHandler extends Handler<ViewSubmissionContext, ViewSubmissionRequest, Response> {
}

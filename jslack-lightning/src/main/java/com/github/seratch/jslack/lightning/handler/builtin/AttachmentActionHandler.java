package com.github.seratch.jslack.lightning.handler.builtin;

import com.github.seratch.jslack.lightning.context.builtin.ActionContext;
import com.github.seratch.jslack.lightning.handler.Handler;
import com.github.seratch.jslack.lightning.request.builtin.AttachmentActionRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface AttachmentActionHandler extends Handler<ActionContext, AttachmentActionRequest, Response> {
}

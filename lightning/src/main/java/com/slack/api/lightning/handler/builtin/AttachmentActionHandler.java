package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.ActionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.AttachmentActionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface AttachmentActionHandler extends Handler<ActionContext, AttachmentActionRequest, Response> {
}

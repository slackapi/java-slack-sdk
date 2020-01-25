package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.DialogSubmissionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.DialogSubmissionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface DialogSubmissionHandler extends Handler<DialogSubmissionContext, DialogSubmissionRequest, Response> {
}

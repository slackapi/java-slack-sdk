package com.slack.api.lightning.handler.builtin;

import com.slack.api.lightning.context.builtin.ViewSubmissionContext;
import com.slack.api.lightning.handler.Handler;
import com.slack.api.lightning.request.builtin.ViewSubmissionRequest;
import com.slack.api.lightning.response.Response;

@FunctionalInterface
public interface ViewSubmissionHandler extends Handler<ViewSubmissionContext, ViewSubmissionRequest, Response> {
}

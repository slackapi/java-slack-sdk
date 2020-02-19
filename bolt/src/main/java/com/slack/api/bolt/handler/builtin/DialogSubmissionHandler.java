package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.DialogSubmissionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.DialogSubmissionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface DialogSubmissionHandler extends Handler<DialogSubmissionContext, DialogSubmissionRequest, Response> {
}

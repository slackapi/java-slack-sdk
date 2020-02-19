package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface ViewSubmissionHandler extends Handler<ViewSubmissionContext, ViewSubmissionRequest, Response> {
}

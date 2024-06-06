package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.WorkflowStepSaveContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.WorkflowStepSaveRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
@Deprecated
public interface WorkflowStepSaveHandler extends Handler<WorkflowStepSaveContext, WorkflowStepSaveRequest, Response> {
}

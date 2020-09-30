package com.slack.api.bolt.handler.builtin;

import com.slack.api.bolt.context.builtin.WorkflowStepExecuteContext;
import com.slack.api.bolt.handler.Handler;
import com.slack.api.bolt.request.builtin.WorkflowStepExecuteRequest;
import com.slack.api.bolt.response.Response;

@FunctionalInterface
public interface WorkflowStepExecuteHandler extends Handler<WorkflowStepExecuteContext, WorkflowStepExecuteRequest, Response> {
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.WorkflowStepExecutePayload;
import com.slack.api.model.event.WorkflowStepExecuteEvent;

public abstract class WorkflowStepExecuteHandler extends EventHandler<WorkflowStepExecutePayload> {

    @Override
    public String getEventType() {
        return WorkflowStepExecuteEvent.TYPE_NAME;
    }
}

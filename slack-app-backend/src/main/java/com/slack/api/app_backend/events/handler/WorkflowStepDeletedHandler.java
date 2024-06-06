package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.WorkflowStepDeletedPayload;
import com.slack.api.model.event.WorkflowStepDeletedEvent;

@Deprecated
public abstract class WorkflowStepDeletedHandler extends EventHandler<WorkflowStepDeletedPayload> {

    @Override
    public String getEventType() {
        return WorkflowStepDeletedEvent.TYPE_NAME;
    }
}

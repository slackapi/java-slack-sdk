package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.WorkflowDeletedPayload;
import com.slack.api.model.event.WorkflowDeletedEvent;

@Deprecated
public abstract class WorkflowDeletedHandler extends EventHandler<WorkflowDeletedPayload> {

    @Override
    public String getEventType() {
        return WorkflowDeletedEvent.TYPE_NAME;
    }
}

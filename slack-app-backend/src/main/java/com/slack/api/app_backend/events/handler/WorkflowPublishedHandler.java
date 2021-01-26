package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.WorkflowPublishedPayload;
import com.slack.api.model.event.WorkflowPublishedEvent;

public abstract class WorkflowPublishedHandler extends EventHandler<WorkflowPublishedPayload> {

    @Override
    public String getEventType() {
        return WorkflowPublishedEvent.TYPE_NAME;
    }
}

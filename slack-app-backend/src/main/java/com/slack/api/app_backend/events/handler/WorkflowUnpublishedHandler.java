package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.WorkflowPublishedPayload;
import com.slack.api.model.event.WorkflowUnpublishedEvent;

public abstract class WorkflowUnpublishedHandler extends EventHandler<WorkflowPublishedPayload> {

    @Override
    public String getEventType() {
        return WorkflowUnpublishedEvent.TYPE_NAME;
    }
}

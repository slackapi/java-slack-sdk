package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.DndUpdatedPayload;
import com.slack.api.model.event.DndUpdatedEvent;

public abstract class DndUpdatedHandler extends EventHandler<DndUpdatedPayload> {

    @Override
    public String getEventType() {
        return DndUpdatedEvent.TYPE_NAME;
    }
}

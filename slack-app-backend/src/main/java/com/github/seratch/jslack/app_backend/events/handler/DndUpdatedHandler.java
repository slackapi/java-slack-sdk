package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.DndUpdatedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.DndUpdatedPayload;

public abstract class DndUpdatedHandler extends EventHandler<DndUpdatedPayload> {

    @Override
    public String getEventType() {
        return DndUpdatedEvent.TYPE_NAME;
    }
}

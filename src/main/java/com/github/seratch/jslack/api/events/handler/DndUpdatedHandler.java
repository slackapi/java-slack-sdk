package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.DndUpdatedPayload;
import com.github.seratch.jslack.api.model.event.DndUpdatedEvent;

public abstract class DndUpdatedHandler extends EventHandler<DndUpdatedPayload> {

    @Override
    public String getEventType() {
        return DndUpdatedEvent.TYPE_NAME;
    }
}

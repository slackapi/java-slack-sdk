package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.DndUpdatedUserPayload;
import com.github.seratch.jslack.api.model.event.DndUpdatedUserEvent;

public abstract class DndUpdatedUserHandler extends EventHandler<DndUpdatedUserPayload> {

    @Override
    public String getEventType() {
        return DndUpdatedUserEvent.TYPE_NAME;
    }
}

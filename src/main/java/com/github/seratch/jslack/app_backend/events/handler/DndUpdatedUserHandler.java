package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.DndUpdatedUserPayload;
import com.github.seratch.jslack.api.model.event.DndUpdatedUserEvent;

public abstract class DndUpdatedUserHandler extends EventHandler<DndUpdatedUserPayload> {

    @Override
    public String getEventType() {
        return DndUpdatedUserEvent.TYPE_NAME;
    }
}

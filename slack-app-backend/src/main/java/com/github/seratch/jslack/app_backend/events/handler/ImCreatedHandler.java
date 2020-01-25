package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ImCreatedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ImCreatedPayload;

public abstract class ImCreatedHandler extends EventHandler<ImCreatedPayload> {

    @Override
    public String getEventType() {
        return ImCreatedEvent.TYPE_NAME;
    }
}

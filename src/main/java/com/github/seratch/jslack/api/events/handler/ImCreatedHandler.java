package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ImCreatedPayload;
import com.github.seratch.jslack.api.model.event.ImCreatedEvent;

public abstract class ImCreatedHandler extends EventHandler<ImCreatedPayload> {

    @Override
    public String getEventType() {
        return ImCreatedEvent.TYPE_NAME;
    }
}

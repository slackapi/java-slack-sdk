package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.PinRemovedPayload;
import com.github.seratch.jslack.api.model.event.PinRemovedEvent;

public abstract class PinRemovedHandler extends EventHandler<PinRemovedPayload> {

    @Override
    public String getEventType() {
        return PinRemovedEvent.TYPE_NAME;
    }
}

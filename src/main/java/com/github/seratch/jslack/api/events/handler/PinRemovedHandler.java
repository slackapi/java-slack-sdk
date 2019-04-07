package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.PinRemovedPayload;
import com.github.seratch.jslack.api.model.event.PinRemovedEvent;

public abstract class PinRemovedHandler extends EventHandler<PinRemovedPayload> {

    @Override
    public String getEventType() {
        return PinRemovedEvent.TYPE_NAME;
    }
}

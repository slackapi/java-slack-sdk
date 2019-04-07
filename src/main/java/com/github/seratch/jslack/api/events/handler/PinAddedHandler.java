package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.PinAddedPayload;
import com.github.seratch.jslack.api.model.event.PinAddedEvent;

public abstract class PinAddedHandler extends EventHandler<PinAddedPayload> {

    @Override
    public String getEventType() {
        return PinAddedEvent.TYPE_NAME;
    }
}

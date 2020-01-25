package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.PinAddedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.PinAddedPayload;

public abstract class PinAddedHandler extends EventHandler<PinAddedPayload> {

    @Override
    public String getEventType() {
        return PinAddedEvent.TYPE_NAME;
    }
}

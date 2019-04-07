package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.StarAddedPayload;
import com.github.seratch.jslack.api.model.event.StarAddedEvent;

public abstract class StarAddedHandler extends EventHandler<StarAddedPayload> {

    @Override
    public String getEventType() {
        return StarAddedEvent.TYPE_NAME;
    }
}

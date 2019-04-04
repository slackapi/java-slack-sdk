package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.StarRemovedPayload;
import com.github.seratch.jslack.api.model.event.StarRemovedEvent;

public abstract class StarRemovedHandler extends EventHandler<StarRemovedPayload> {

    @Override
    public String getEventType() {
        return StarRemovedEvent.TYPE_NAME;
    }
}

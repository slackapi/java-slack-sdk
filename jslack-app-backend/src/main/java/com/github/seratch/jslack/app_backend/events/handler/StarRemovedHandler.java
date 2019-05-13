package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.StarRemovedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.StarRemovedPayload;

public abstract class StarRemovedHandler extends EventHandler<StarRemovedPayload> {

    @Override
    public String getEventType() {
        return StarRemovedEvent.TYPE_NAME;
    }
}

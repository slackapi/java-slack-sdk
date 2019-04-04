package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ResourcesRemovedPayload;
import com.github.seratch.jslack.api.model.event.ResourcesRemovedEvent;

public abstract class ResourcesRemovedHandler extends EventHandler<ResourcesRemovedPayload> {

    @Override
    public String getEventType() {
        return ResourcesRemovedEvent.TYPE_NAME;
    }
}

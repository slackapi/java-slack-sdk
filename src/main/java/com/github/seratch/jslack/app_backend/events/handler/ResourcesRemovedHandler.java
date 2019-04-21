package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ResourcesRemovedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ResourcesRemovedPayload;

public abstract class ResourcesRemovedHandler extends EventHandler<ResourcesRemovedPayload> {

    @Override
    public String getEventType() {
        return ResourcesRemovedEvent.TYPE_NAME;
    }
}

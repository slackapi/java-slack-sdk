package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ResourcesAddedPayload;
import com.github.seratch.jslack.api.model.event.ResourcesAddedEvent;

public abstract class ResourcesAddedHandler extends EventHandler<ResourcesAddedPayload> {

    @Override
    public String getEventType() {
        return ResourcesAddedEvent.TYPE_NAME;
    }
}

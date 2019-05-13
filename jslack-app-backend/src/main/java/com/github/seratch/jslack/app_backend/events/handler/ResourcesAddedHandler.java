package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ResourcesAddedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ResourcesAddedPayload;

public abstract class ResourcesAddedHandler extends EventHandler<ResourcesAddedPayload> {

    @Override
    public String getEventType() {
        return ResourcesAddedEvent.TYPE_NAME;
    }
}

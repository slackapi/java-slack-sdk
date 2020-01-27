package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.ResourcesAddedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ResourcesAddedPayload;

public abstract class ResourcesAddedHandler extends EventHandler<ResourcesAddedPayload> {

    @Override
    public String getEventType() {
        return ResourcesAddedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ResourcesRemovedPayload;
import com.slack.api.model.event.ResourcesRemovedEvent;

public abstract class ResourcesRemovedHandler extends EventHandler<ResourcesRemovedPayload> {

    @Override
    public String getEventType() {
        return ResourcesRemovedEvent.TYPE_NAME;
    }
}

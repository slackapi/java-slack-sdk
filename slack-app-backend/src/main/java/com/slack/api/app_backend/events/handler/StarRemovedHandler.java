package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.StarRemovedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.StarRemovedPayload;

public abstract class StarRemovedHandler extends EventHandler<StarRemovedPayload> {

    @Override
    public String getEventType() {
        return StarRemovedEvent.TYPE_NAME;
    }
}

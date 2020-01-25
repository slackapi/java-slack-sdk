package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.PinRemovedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.PinRemovedPayload;

public abstract class PinRemovedHandler extends EventHandler<PinRemovedPayload> {

    @Override
    public String getEventType() {
        return PinRemovedEvent.TYPE_NAME;
    }
}

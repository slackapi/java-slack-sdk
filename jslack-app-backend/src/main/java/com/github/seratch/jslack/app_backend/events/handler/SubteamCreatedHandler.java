package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.SubteamCreatedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamCreatedPayload;

public abstract class SubteamCreatedHandler extends EventHandler<SubteamCreatedPayload> {

    @Override
    public String getEventType() {
        return SubteamCreatedEvent.TYPE_NAME;
    }
}

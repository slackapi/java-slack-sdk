package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamCreatedPayload;
import com.github.seratch.jslack.api.model.event.SubteamCreatedEvent;

public abstract class SubteamCreatedHandler extends EventHandler<SubteamCreatedPayload> {

    @Override
    public String getEventType() {
        return SubteamCreatedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamUpdatedPayload;
import com.github.seratch.jslack.api.model.event.SubteamUpdatedEvent;

public abstract class SubteamUpdatedHandler extends EventHandler<SubteamUpdatedPayload> {

    @Override
    public String getEventType() {
        return SubteamUpdatedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.SubteamSelfAddedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamSelfAddedPayload;

public abstract class SubteamSelfAddedHandler extends EventHandler<SubteamSelfAddedPayload> {

    @Override
    public String getEventType() {
        return SubteamSelfAddedEvent.TYPE_NAME;
    }
}

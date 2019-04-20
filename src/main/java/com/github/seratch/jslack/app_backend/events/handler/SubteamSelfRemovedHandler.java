package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.SubteamSelfRemovedPayload;
import com.github.seratch.jslack.api.model.event.SubteamSelfRemovedEvent;

public abstract class SubteamSelfRemovedHandler extends EventHandler<SubteamSelfRemovedPayload> {

    @Override
    public String getEventType() {
        return SubteamSelfRemovedEvent.TYPE_NAME;
    }
}

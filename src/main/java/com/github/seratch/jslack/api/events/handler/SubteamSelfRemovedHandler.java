package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.SubteamSelfRemovedPayload;
import com.github.seratch.jslack.api.model.event.SubteamSelfRemovedEvent;

public abstract class SubteamSelfRemovedHandler extends EventHandler<SubteamSelfRemovedPayload> {

    @Override
    public String getEventType() {
        return SubteamSelfRemovedEvent.TYPE_NAME;
    }
}

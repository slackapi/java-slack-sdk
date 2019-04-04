package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.SubteamSelfAddedPayload;
import com.github.seratch.jslack.api.model.event.SubteamSelfAddedEvent;

public abstract class SubteamSelfAddedHandler extends EventHandler<SubteamSelfAddedPayload> {

    @Override
    public String getEventType() {
        return SubteamSelfAddedEvent.TYPE_NAME;
    }
}

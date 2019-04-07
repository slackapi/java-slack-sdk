package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.SubteamCreatedPayload;
import com.github.seratch.jslack.api.model.event.SubteamCreatedEvent;

public abstract class SubteamCreatedHandler extends EventHandler<SubteamCreatedPayload> {

    @Override
    public String getEventType() {
        return SubteamCreatedEvent.TYPE_NAME;
    }
}

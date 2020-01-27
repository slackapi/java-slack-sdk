package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.SubteamSelfRemovedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SubteamSelfRemovedPayload;

public abstract class SubteamSelfRemovedHandler extends EventHandler<SubteamSelfRemovedPayload> {

    @Override
    public String getEventType() {
        return SubteamSelfRemovedEvent.TYPE_NAME;
    }
}

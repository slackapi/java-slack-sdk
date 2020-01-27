package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.SubteamCreatedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SubteamCreatedPayload;

public abstract class SubteamCreatedHandler extends EventHandler<SubteamCreatedPayload> {

    @Override
    public String getEventType() {
        return SubteamCreatedEvent.TYPE_NAME;
    }
}

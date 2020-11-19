package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamsAccessGrantedPayload;
import com.slack.api.model.event.TeamsAccessGrantedEvent;

public abstract class TeamsAccessGrantedHandler extends EventHandler<TeamsAccessGrantedPayload> {

    @Override
    public String getEventType() {
        return TeamsAccessGrantedEvent.TYPE_NAME;
    }
}

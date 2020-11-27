package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamAccessGrantedPayload;
import com.slack.api.model.event.TeamAccessGrantedEvent;

public abstract class TeamAccessGrantedHandler extends EventHandler<TeamAccessGrantedPayload> {

    @Override
    public String getEventType() {
        return TeamAccessGrantedEvent.TYPE_NAME;
    }
}

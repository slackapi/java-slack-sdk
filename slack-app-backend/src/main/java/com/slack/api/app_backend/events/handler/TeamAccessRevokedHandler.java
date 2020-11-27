package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamAccessRevokedPayload;
import com.slack.api.model.event.TeamAccessRevokedEvent;

public abstract class TeamAccessRevokedHandler extends EventHandler<TeamAccessRevokedPayload> {

    @Override
    public String getEventType() {
        return TeamAccessRevokedEvent.TYPE_NAME;
    }
}

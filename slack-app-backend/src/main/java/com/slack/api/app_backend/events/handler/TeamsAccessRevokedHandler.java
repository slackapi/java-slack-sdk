package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamsAccessRevokedPayload;
import com.slack.api.model.event.TeamsAccessRevokedEvent;

public abstract class TeamsAccessRevokedHandler extends EventHandler<TeamsAccessRevokedPayload> {

    @Override
    public String getEventType() {
        return TeamsAccessRevokedEvent.TYPE_NAME;
    }
}

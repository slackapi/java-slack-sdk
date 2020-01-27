package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.TeamDomainChangeEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamDomainChangePayload;

public abstract class TeamDomainChangeHandler extends EventHandler<TeamDomainChangePayload> {

    @Override
    public String getEventType() {
        return TeamDomainChangeEvent.TYPE_NAME;
    }
}

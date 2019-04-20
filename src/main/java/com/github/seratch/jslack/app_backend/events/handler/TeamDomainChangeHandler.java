package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.TeamDomainChangePayload;
import com.github.seratch.jslack.api.model.event.TeamDomainChangeEvent;

public abstract class TeamDomainChangeHandler extends EventHandler<TeamDomainChangePayload> {

    @Override
    public String getEventType() {
        return TeamDomainChangeEvent.TYPE_NAME;
    }
}

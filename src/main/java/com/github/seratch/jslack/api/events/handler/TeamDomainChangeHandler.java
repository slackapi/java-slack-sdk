package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.TeamDomainChangePayload;
import com.github.seratch.jslack.api.model.event.TeamDomainChangeEvent;

public abstract class TeamDomainChangeHandler extends EventHandler<TeamDomainChangePayload> {

    @Override
    public String getEventType() {
        return TeamDomainChangeEvent.TYPE_NAME;
    }
}

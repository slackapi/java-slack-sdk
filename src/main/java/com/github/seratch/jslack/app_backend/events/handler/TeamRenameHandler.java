package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.TeamRenamePayload;
import com.github.seratch.jslack.api.model.event.TeamRenameEvent;

public abstract class TeamRenameHandler extends EventHandler<TeamRenamePayload> {

    @Override
    public String getEventType() {
        return TeamRenameEvent.TYPE_NAME;
    }
}

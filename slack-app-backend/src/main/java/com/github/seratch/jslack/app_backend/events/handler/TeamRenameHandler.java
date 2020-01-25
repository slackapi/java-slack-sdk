package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.TeamRenameEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.TeamRenamePayload;

public abstract class TeamRenameHandler extends EventHandler<TeamRenamePayload> {

    @Override
    public String getEventType() {
        return TeamRenameEvent.TYPE_NAME;
    }
}

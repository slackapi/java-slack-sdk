package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TeamJoinPayload;
import com.slack.api.model.event.TeamJoinEvent;

public abstract class TeamJoinHandler extends EventHandler<TeamJoinPayload> {

    @Override
    public String getEventType() {
        return TeamJoinEvent.TYPE_NAME;
    }
}

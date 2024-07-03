package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppUninstalledTeamPayload;
import com.slack.api.model.event.AppUninstalledTeamEvent;

public abstract class AppUninstalledTeamHandler extends EventHandler<AppUninstalledTeamPayload> {

    @Override
    public String getEventType() {
        return AppUninstalledTeamEvent.TYPE_NAME;
    }
}

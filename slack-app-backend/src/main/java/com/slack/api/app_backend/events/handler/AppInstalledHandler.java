package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppInstalledPayload;
import com.slack.api.model.event.AppInstalledEvent;

public abstract class AppInstalledHandler extends EventHandler<AppInstalledPayload> {

    @Override
    public String getEventType() {
        return AppInstalledEvent.TYPE_NAME;
    }
}

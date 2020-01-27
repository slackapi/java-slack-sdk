package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.AppUninstalledEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppUninstalledPayload;

public abstract class AppUninstalledHandler extends EventHandler<AppUninstalledPayload> {

    @Override
    public String getEventType() {
        return AppUninstalledEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppRequestedPayload;
import com.slack.api.model.event.AppRequestedEvent;

public abstract class AppRequestedHandler extends EventHandler<AppRequestedPayload> {

    @Override
    public String getEventType() {
        return AppRequestedEvent.TYPE_NAME;
    }
}

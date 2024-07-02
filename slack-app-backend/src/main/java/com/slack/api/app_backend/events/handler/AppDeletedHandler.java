package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppDeletedPayload;
import com.slack.api.model.event.AppDeletedEvent;

public abstract class AppDeletedHandler extends EventHandler<AppDeletedPayload> {

    @Override
    public String getEventType() {
        return AppDeletedEvent.TYPE_NAME;
    }
}

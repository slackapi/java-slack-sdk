package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.AppRateLimitedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppRateLimitedPayload;

public abstract class AppRateLimitedHandler extends EventHandler<AppRateLimitedPayload> {

    @Override
    public String getEventType() {
        return AppRateLimitedEvent.TYPE_NAME;
    }
}

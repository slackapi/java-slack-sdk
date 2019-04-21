package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.AppRateLimitedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppRateLimitedPayload;

public abstract class AppRateLimitedHandler extends EventHandler<AppRateLimitedPayload> {

    @Override
    public String getEventType() {
        return AppRateLimitedEvent.TYPE_NAME;
    }
}

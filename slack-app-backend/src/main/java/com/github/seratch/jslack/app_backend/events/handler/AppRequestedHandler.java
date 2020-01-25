package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.AppRequestedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppRequestedPayload;

public abstract class AppRequestedHandler extends EventHandler<AppRequestedPayload> {

    @Override
    public String getEventType() {
        return AppRequestedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.AppHomeOpenedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppHomeOpenedPayload;

public abstract class AppHomeOpenedHandler extends EventHandler<AppHomeOpenedPayload> {

    @Override
    public String getEventType() {
        return AppHomeOpenedEvent.TYPE_NAME;
    }
}

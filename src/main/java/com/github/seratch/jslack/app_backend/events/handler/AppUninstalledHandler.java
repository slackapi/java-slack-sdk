package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppUninstalledPayload;
import com.github.seratch.jslack.api.model.event.AppUninstalledEvent;

public abstract class AppUninstalledHandler extends EventHandler<AppUninstalledPayload> {

    @Override
    public String getEventType() {
        return AppUninstalledEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.AppUninstalledEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppUninstalledPayload;

public abstract class AppUninstalledHandler extends EventHandler<AppUninstalledPayload> {

    @Override
    public String getEventType() {
        return AppUninstalledEvent.TYPE_NAME;
    }
}

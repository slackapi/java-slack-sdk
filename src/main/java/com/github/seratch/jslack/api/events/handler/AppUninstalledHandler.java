package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.AppUninstalledPayload;
import com.github.seratch.jslack.api.model.event.AppUninstalledEvent;

public abstract class AppUninstalledHandler extends EventHandler<AppUninstalledPayload> {

    @Override
    public String getEventType() {
        return AppUninstalledEvent.TYPE_NAME;
    }
}

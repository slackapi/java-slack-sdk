package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GoodbyePayload;
import com.github.seratch.jslack.api.model.event.GoodbyeEvent;

public abstract class GoodbyeHandler extends EventHandler<GoodbyePayload> {

    @Override
    public String getEventType() {
        return GoodbyeEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GoodbyePayload;
import com.github.seratch.jslack.api.model.event.GoodbyeEvent;

public abstract class GoodbyeHandler extends EventHandler<GoodbyePayload> {

    @Override
    public String getEventType() {
        return GoodbyeEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ImOpenPayload;
import com.github.seratch.jslack.api.model.event.ImOpenEvent;

public abstract class ImOpenHandler extends EventHandler<ImOpenPayload> {

    @Override
    public String getEventType() {
        return ImOpenEvent.TYPE_NAME;
    }
}

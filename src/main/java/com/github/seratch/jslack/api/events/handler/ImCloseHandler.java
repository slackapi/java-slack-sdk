package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ImClosePayload;
import com.github.seratch.jslack.api.model.event.ImCloseEvent;

public abstract class ImCloseHandler extends EventHandler<ImClosePayload> {

    @Override
    public String getEventType() {
        return ImCloseEvent.TYPE_NAME;
    }
}

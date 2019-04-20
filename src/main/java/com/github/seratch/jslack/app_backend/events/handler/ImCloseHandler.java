package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ImClosePayload;
import com.github.seratch.jslack.api.model.event.ImCloseEvent;

public abstract class ImCloseHandler extends EventHandler<ImClosePayload> {

    @Override
    public String getEventType() {
        return ImCloseEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ImCloseEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ImClosePayload;

public abstract class ImCloseHandler extends EventHandler<ImClosePayload> {

    @Override
    public String getEventType() {
        return ImCloseEvent.TYPE_NAME;
    }
}

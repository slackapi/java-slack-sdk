package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ImOpenPayload;
import com.github.seratch.jslack.api.model.event.ImOpenEvent;

public abstract class ImOpenHandler extends EventHandler<ImOpenPayload> {

    @Override
    public String getEventType() {
        return ImOpenEvent.TYPE_NAME;
    }
}

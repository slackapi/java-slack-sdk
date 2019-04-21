package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ScopeDeniedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ScopeDeniedPayload;

public abstract class ScopeDeniedHandler extends EventHandler<ScopeDeniedPayload> {

    @Override
    public String getEventType() {
        return ScopeDeniedEvent.TYPE_NAME;
    }
}

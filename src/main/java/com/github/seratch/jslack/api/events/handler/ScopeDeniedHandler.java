package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ScopeDeniedPayload;
import com.github.seratch.jslack.api.model.event.ScopeDeniedEvent;

public abstract class ScopeDeniedHandler extends EventHandler<ScopeDeniedPayload> {

    @Override
    public String getEventType() {
        return ScopeDeniedEvent.TYPE_NAME;
    }
}

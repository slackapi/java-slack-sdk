package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ScopeGrantedPayload;
import com.github.seratch.jslack.api.model.event.ScopeGrantedEvent;

public abstract class ScopeGrantedHandler extends EventHandler<ScopeGrantedPayload> {

    @Override
    public String getEventType() {
        return ScopeGrantedEvent.TYPE_NAME;
    }
}

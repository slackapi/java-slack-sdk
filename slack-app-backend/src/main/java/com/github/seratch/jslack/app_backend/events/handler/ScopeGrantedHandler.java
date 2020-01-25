package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.ScopeGrantedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ScopeGrantedPayload;

public abstract class ScopeGrantedHandler extends EventHandler<ScopeGrantedPayload> {

    @Override
    public String getEventType() {
        return ScopeGrantedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ScopeGrantedPayload;
import com.slack.api.model.event.ScopeGrantedEvent;

public abstract class ScopeGrantedHandler extends EventHandler<ScopeGrantedPayload> {

    @Override
    public String getEventType() {
        return ScopeGrantedEvent.TYPE_NAME;
    }
}

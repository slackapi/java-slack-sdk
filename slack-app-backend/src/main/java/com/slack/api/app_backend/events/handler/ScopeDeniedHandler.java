package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.ScopeDeniedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ScopeDeniedPayload;

public abstract class ScopeDeniedHandler extends EventHandler<ScopeDeniedPayload> {

    @Override
    public String getEventType() {
        return ScopeDeniedEvent.TYPE_NAME;
    }
}

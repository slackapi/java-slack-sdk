package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.LinkSharedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.LinkSharedPayload;

public abstract class LinkSharedHandler extends EventHandler<LinkSharedPayload> {

    @Override
    public String getEventType() {
        return LinkSharedEvent.TYPE_NAME;
    }
}

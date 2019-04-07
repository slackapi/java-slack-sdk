package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.LinkSharedPayload;
import com.github.seratch.jslack.api.model.event.LinkSharedEvent;

public abstract class LinkSharedHandler extends EventHandler<LinkSharedPayload> {

    @Override
    public String getEventType() {
        return LinkSharedEvent.TYPE_NAME;
    }
}

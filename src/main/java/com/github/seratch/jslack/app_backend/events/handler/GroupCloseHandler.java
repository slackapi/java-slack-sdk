package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupClosePayload;
import com.github.seratch.jslack.api.model.event.GroupCloseEvent;

public abstract class GroupCloseHandler extends EventHandler<GroupClosePayload> {

    @Override
    public String getEventType() {
        return GroupCloseEvent.TYPE_NAME;
    }
}

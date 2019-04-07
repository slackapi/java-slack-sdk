package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupClosePayload;
import com.github.seratch.jslack.api.model.event.GroupCloseEvent;

public abstract class GroupCloseHandler extends EventHandler<GroupClosePayload> {

    @Override
    public String getEventType() {
        return GroupCloseEvent.TYPE_NAME;
    }
}

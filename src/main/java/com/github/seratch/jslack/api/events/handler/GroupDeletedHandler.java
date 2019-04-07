package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupDeletedPayload;
import com.github.seratch.jslack.api.model.event.GroupDeletedEvent;

public abstract class GroupDeletedHandler extends EventHandler<GroupDeletedPayload> {

    @Override
    public String getEventType() {
        return GroupDeletedEvent.TYPE_NAME;
    }
}

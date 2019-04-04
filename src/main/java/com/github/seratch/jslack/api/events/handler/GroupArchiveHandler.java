package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupArchivePayload;
import com.github.seratch.jslack.api.model.event.GroupArchiveEvent;

public abstract class GroupArchiveHandler extends EventHandler<GroupArchivePayload> {

    @Override
    public String getEventType() {
        return GroupArchiveEvent.TYPE_NAME;
    }
}

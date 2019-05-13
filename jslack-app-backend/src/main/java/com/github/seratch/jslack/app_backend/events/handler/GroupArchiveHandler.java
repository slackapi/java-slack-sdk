package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GroupArchiveEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupArchivePayload;

public abstract class GroupArchiveHandler extends EventHandler<GroupArchivePayload> {

    @Override
    public String getEventType() {
        return GroupArchiveEvent.TYPE_NAME;
    }
}

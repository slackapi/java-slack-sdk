package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GroupOpenEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupOpenPayload;

public abstract class GroupOpenHandler extends EventHandler<GroupOpenPayload> {

    @Override
    public String getEventType() {
        return GroupOpenEvent.TYPE_NAME;
    }
}

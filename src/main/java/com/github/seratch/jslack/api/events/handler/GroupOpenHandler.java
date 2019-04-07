package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupOpenPayload;
import com.github.seratch.jslack.api.model.event.GroupOpenEvent;

public abstract class GroupOpenHandler extends EventHandler<GroupOpenPayload> {

    @Override
    public String getEventType() {
        return GroupOpenEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupUnarchivePayload;
import com.github.seratch.jslack.api.model.event.GroupUnarchiveEvent;

public abstract class GroupUnarchiveHandler extends EventHandler<GroupUnarchivePayload> {

    @Override
    public String getEventType() {
        return GroupUnarchiveEvent.TYPE_NAME;
    }
}

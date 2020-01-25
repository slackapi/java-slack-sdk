package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.GroupUnarchiveEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupUnarchivePayload;

public abstract class GroupUnarchiveHandler extends EventHandler<GroupUnarchivePayload> {

    @Override
    public String getEventType() {
        return GroupUnarchiveEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupLeftPayload;
import com.github.seratch.jslack.api.model.event.GroupLeftEvent;

public abstract class GroupLeftHandler extends EventHandler<GroupLeftPayload> {

    @Override
    public String getEventType() {
        return GroupLeftEvent.TYPE_NAME;
    }
}

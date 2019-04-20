package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupLeftPayload;
import com.github.seratch.jslack.api.model.event.GroupLeftEvent;

public abstract class GroupLeftHandler extends EventHandler<GroupLeftPayload> {

    @Override
    public String getEventType() {
        return GroupLeftEvent.TYPE_NAME;
    }
}

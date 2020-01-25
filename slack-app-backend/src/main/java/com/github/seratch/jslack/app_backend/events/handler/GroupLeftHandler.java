package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.GroupLeftEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupLeftPayload;

public abstract class GroupLeftHandler extends EventHandler<GroupLeftPayload> {

    @Override
    public String getEventType() {
        return GroupLeftEvent.TYPE_NAME;
    }
}

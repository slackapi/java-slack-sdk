package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupLeftPayload;
import com.slack.api.model.event.GroupLeftEvent;

public abstract class GroupLeftHandler extends EventHandler<GroupLeftPayload> {

    @Override
    public String getEventType() {
        return GroupLeftEvent.TYPE_NAME;
    }
}

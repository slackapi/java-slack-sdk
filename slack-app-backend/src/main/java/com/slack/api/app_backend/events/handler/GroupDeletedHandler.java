package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.GroupDeletedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupDeletedPayload;

public abstract class GroupDeletedHandler extends EventHandler<GroupDeletedPayload> {

    @Override
    public String getEventType() {
        return GroupDeletedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.GroupArchiveEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupArchivePayload;

public abstract class GroupArchiveHandler extends EventHandler<GroupArchivePayload> {

    @Override
    public String getEventType() {
        return GroupArchiveEvent.TYPE_NAME;
    }
}

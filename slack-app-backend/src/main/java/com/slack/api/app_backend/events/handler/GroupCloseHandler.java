package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupClosePayload;
import com.slack.api.model.event.GroupCloseEvent;

public abstract class GroupCloseHandler extends EventHandler<GroupClosePayload> {

    @Override
    public String getEventType() {
        return GroupCloseEvent.TYPE_NAME;
    }
}

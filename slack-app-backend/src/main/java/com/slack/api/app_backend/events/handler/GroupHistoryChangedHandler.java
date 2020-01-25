package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.GroupHistoryChangedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupHistoryChangedPayload;

public abstract class GroupHistoryChangedHandler extends EventHandler<GroupHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return GroupHistoryChangedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GroupRenamePayload;
import com.slack.api.model.event.GroupRenameEvent;

public abstract class GroupRenameHandler extends EventHandler<GroupRenamePayload> {

    @Override
    public String getEventType() {
        return GroupRenameEvent.TYPE_NAME;
    }
}

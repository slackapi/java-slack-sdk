package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GroupRenameEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GroupRenamePayload;

public abstract class GroupRenameHandler extends EventHandler<GroupRenamePayload> {

    @Override
    public String getEventType() {
        return GroupRenameEvent.TYPE_NAME;
    }
}

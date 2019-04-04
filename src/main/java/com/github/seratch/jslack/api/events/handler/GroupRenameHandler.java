package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupRenamePayload;
import com.github.seratch.jslack.api.model.event.GroupRenameEvent;

public abstract class GroupRenameHandler extends EventHandler<GroupRenamePayload> {

    @Override
    public String getEventType() {
        return GroupRenameEvent.TYPE_NAME;
    }
}

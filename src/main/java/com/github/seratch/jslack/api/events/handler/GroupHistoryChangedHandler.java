package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GroupHistoryChangedPayload;
import com.github.seratch.jslack.api.model.event.GroupHistoryChangedEvent;

public abstract class GroupHistoryChangedHandler extends EventHandler<GroupHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return GroupHistoryChangedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelRenamePayload;
import com.github.seratch.jslack.api.model.event.ChannelRenameEvent;

public abstract class ChannelRenameHandler extends EventHandler<ChannelRenamePayload> {

    @Override
    public String getEventType() {
        return ChannelRenameEvent.TYPE_NAME;
    }
}

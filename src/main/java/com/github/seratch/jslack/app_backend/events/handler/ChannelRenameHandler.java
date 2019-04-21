package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ChannelRenameEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelRenamePayload;

public abstract class ChannelRenameHandler extends EventHandler<ChannelRenamePayload> {

    @Override
    public String getEventType() {
        return ChannelRenameEvent.TYPE_NAME;
    }
}

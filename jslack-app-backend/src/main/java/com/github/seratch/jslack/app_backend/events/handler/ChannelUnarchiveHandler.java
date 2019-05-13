package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ChannelUnarchiveEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelUnarchivePayload;

public abstract class ChannelUnarchiveHandler extends EventHandler<ChannelUnarchivePayload> {

    @Override
    public String getEventType() {
        return ChannelUnarchiveEvent.TYPE_NAME;
    }
}

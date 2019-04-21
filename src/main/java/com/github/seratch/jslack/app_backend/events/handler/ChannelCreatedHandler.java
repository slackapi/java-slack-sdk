package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ChannelCreatedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelCreatedPayload;

public abstract class ChannelCreatedHandler extends EventHandler<ChannelCreatedPayload> {

    @Override
    public String getEventType() {
        return ChannelCreatedEvent.TYPE_NAME;
    }
}

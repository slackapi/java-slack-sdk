package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ChannelCreatedPayload;
import com.github.seratch.jslack.api.model.event.ChannelCreatedEvent;

public abstract class ChannelCreatedHandler extends EventHandler<ChannelCreatedPayload> {

    @Override
    public String getEventType() {
        return ChannelCreatedEvent.TYPE_NAME;
    }
}

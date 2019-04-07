package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ChannelDeletedPayload;
import com.github.seratch.jslack.api.model.event.ChannelDeletedEvent;

public abstract class ChannelDeletedHandler extends EventHandler<ChannelDeletedPayload> {

    @Override
    public String getEventType() {
        return ChannelDeletedEvent.TYPE_NAME;
    }
}

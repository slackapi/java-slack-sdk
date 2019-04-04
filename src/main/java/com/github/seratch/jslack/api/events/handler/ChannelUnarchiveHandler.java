package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ChannelUnarchivePayload;
import com.github.seratch.jslack.api.model.event.ChannelUnarchiveEvent;

public abstract class ChannelUnarchiveHandler extends EventHandler<ChannelUnarchivePayload> {

    @Override
    public String getEventType() {
        return ChannelUnarchiveEvent.TYPE_NAME;
    }
}

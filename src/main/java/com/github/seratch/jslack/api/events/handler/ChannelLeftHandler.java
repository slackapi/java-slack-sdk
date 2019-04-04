package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ChannelLeftPayload;
import com.github.seratch.jslack.api.model.event.ChannelLeftEvent;

public abstract class ChannelLeftHandler extends EventHandler<ChannelLeftPayload> {

    @Override
    public String getEventType() {
        return ChannelLeftEvent.TYPE_NAME;
    }
}

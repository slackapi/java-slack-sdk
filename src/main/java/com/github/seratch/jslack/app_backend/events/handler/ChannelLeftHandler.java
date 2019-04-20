package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelLeftPayload;
import com.github.seratch.jslack.api.model.event.ChannelLeftEvent;

public abstract class ChannelLeftHandler extends EventHandler<ChannelLeftPayload> {

    @Override
    public String getEventType() {
        return ChannelLeftEvent.TYPE_NAME;
    }
}

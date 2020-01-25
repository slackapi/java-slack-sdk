package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.ChannelLeftEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelLeftPayload;

public abstract class ChannelLeftHandler extends EventHandler<ChannelLeftPayload> {

    @Override
    public String getEventType() {
        return ChannelLeftEvent.TYPE_NAME;
    }
}

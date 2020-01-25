package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.ChannelDeletedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelDeletedPayload;

public abstract class ChannelDeletedHandler extends EventHandler<ChannelDeletedPayload> {

    @Override
    public String getEventType() {
        return ChannelDeletedEvent.TYPE_NAME;
    }
}

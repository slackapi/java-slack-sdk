package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ChannelDeletedPayload;
import com.slack.api.model.event.ChannelDeletedEvent;

public abstract class ChannelDeletedHandler extends EventHandler<ChannelDeletedPayload> {

    @Override
    public String getEventType() {
        return ChannelDeletedEvent.TYPE_NAME;
    }
}

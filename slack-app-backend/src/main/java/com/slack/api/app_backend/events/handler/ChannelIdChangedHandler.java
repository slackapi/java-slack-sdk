package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ChannelIdChangedPayload;
import com.slack.api.model.event.ChannelIdChangedEvent;

public abstract class ChannelIdChangedHandler extends EventHandler<ChannelIdChangedPayload> {

    @Override
    public String getEventType() {
        return ChannelIdChangedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ChannelHistoryChangedPayload;
import com.github.seratch.jslack.api.model.event.ChannelHistoryChangedEvent;

public abstract class ChannelHistoryChangedHandler extends EventHandler<ChannelHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return ChannelHistoryChangedEvent.TYPE_NAME;
    }
}

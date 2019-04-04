package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ChannelHistoryChangedPayload;
import com.github.seratch.jslack.api.model.event.ChannelHistoryChangedEvent;

public abstract class ChannelHistoryChangedHandler extends EventHandler<ChannelHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return ChannelHistoryChangedEvent.TYPE_NAME;
    }
}

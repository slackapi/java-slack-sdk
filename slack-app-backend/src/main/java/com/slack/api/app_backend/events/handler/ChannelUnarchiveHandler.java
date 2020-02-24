package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ChannelUnarchivePayload;
import com.slack.api.model.event.ChannelUnarchiveEvent;

public abstract class ChannelUnarchiveHandler extends EventHandler<ChannelUnarchivePayload> {

    @Override
    public String getEventType() {
        return ChannelUnarchiveEvent.TYPE_NAME;
    }
}

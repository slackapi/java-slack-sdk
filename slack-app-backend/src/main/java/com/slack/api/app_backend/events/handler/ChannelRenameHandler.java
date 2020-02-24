package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ChannelRenamePayload;
import com.slack.api.model.event.ChannelRenameEvent;

public abstract class ChannelRenameHandler extends EventHandler<ChannelRenamePayload> {

    @Override
    public String getEventType() {
        return ChannelRenameEvent.TYPE_NAME;
    }
}

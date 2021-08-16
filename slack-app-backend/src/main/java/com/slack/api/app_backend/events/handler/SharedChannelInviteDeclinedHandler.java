package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SharedChannelInviteDeclinedPayload;
import com.slack.api.model.event.SharedChannelInviteDeclinedEvent;

public abstract class SharedChannelInviteDeclinedHandler extends EventHandler<SharedChannelInviteDeclinedPayload> {

    @Override
    public String getEventType() {
        return SharedChannelInviteDeclinedEvent.TYPE_NAME;
    }
}

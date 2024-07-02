package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SharedChannelInviteRequestedPayload;
import com.slack.api.model.event.SharedChannelInviteRequestedEvent;

public abstract class SharedChannelInviteRequestedHandler extends EventHandler<SharedChannelInviteRequestedPayload> {

    @Override
    public String getEventType() {
        return SharedChannelInviteRequestedEvent.TYPE_NAME;
    }
}

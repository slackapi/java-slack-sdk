package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SharedChannelInviteAcceptedPayload;
import com.slack.api.model.event.SharedChannelInviteAcceptedEvent;

public abstract class SharedChannelInviteAcceptedHandler extends EventHandler<SharedChannelInviteAcceptedPayload> {

    @Override
    public String getEventType() {
        return SharedChannelInviteAcceptedEvent.TYPE_NAME;
    }
}

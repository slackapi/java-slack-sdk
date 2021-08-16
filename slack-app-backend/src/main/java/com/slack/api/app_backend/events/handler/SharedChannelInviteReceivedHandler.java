package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SharedChannelInviteReceivedPayload;
import com.slack.api.model.event.SharedChannelInviteReceivedEvent;

public abstract class SharedChannelInviteReceivedHandler extends EventHandler<SharedChannelInviteReceivedPayload> {

    @Override
    public String getEventType() {
        return SharedChannelInviteReceivedEvent.TYPE_NAME;
    }
}

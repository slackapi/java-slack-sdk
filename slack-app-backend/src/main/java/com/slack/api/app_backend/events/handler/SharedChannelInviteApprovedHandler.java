package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SharedChannelInviteApprovedPayload;
import com.slack.api.model.event.SharedChannelInviteApprovedEvent;

public abstract class SharedChannelInviteApprovedHandler extends EventHandler<SharedChannelInviteApprovedPayload> {

    @Override
    public String getEventType() {
        return SharedChannelInviteApprovedEvent.TYPE_NAME;
    }
}

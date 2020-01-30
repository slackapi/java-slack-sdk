package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.InviteRequestedPayload;
import com.slack.api.model.event.InviteRequestedEvent;

public abstract class InviteRequestedHandler extends EventHandler<InviteRequestedPayload> {

    @Override
    public String getEventType() {
        return InviteRequestedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserConnectionStatusRequestedPayload;
import com.slack.api.model.event.UserConnectionStatusRequestedEvent;

public abstract class UserConnectionStatusRequestedHandler extends EventHandler<UserConnectionStatusRequestedPayload> {

    @Override
    public String getEventType() {
        return UserConnectionStatusRequestedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserStatusChangedPayload;
import com.slack.api.model.event.UserStatusChangedEvent;

public abstract class UserStatusChangedHandler extends EventHandler<UserStatusChangedPayload> {

    @Override
    public String getEventType() {
        return UserStatusChangedEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserProfileChangedPayload;
import com.slack.api.model.event.UserProfileChangedEvent;

public abstract class UserProfileChangedHandler extends EventHandler<UserProfileChangedPayload> {

    @Override
    public String getEventType() {
        return UserProfileChangedEvent.TYPE_NAME;
    }
}

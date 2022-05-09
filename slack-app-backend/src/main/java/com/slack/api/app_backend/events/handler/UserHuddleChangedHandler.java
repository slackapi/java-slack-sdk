package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserHuddleChangedPayload;
import com.slack.api.model.event.UserHuddleChangedEvent;

public abstract class UserHuddleChangedHandler extends EventHandler<UserHuddleChangedPayload> {

    @Override
    public String getEventType() {
        return UserHuddleChangedEvent.TYPE_NAME;
    }
}

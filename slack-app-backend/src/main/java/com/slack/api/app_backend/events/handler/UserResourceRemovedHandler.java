package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.UserResourceRemovedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserResourceRemovedPayload;

public abstract class UserResourceRemovedHandler extends EventHandler<UserResourceRemovedPayload> {

    @Override
    public String getEventType() {
        return UserResourceRemovedEvent.TYPE_NAME;
    }
}

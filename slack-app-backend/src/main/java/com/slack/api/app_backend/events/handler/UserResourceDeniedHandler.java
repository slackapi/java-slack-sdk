package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserResourceDeniedPayload;
import com.slack.api.model.event.UserResourceDeniedEvent;

public abstract class UserResourceDeniedHandler extends EventHandler<UserResourceDeniedPayload> {

    @Override
    public String getEventType() {
        return UserResourceDeniedEvent.TYPE_NAME;
    }
}

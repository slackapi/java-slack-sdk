package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.UserResourceDeniedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.UserResourceDeniedPayload;

public abstract class UserResourceDeniedHandler extends EventHandler<UserResourceDeniedPayload> {

    @Override
    public String getEventType() {
        return UserResourceDeniedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.UserResourceDeniedPayload;
import com.github.seratch.jslack.api.model.event.UserResourceDeniedEvent;

public abstract class UserResourceDeniedHandler extends EventHandler<UserResourceDeniedPayload> {

    @Override
    public String getEventType() {
        return UserResourceDeniedEvent.TYPE_NAME;
    }
}

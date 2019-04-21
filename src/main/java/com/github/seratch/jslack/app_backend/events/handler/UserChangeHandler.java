package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.UserChangeEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.UserChangePayload;

public abstract class UserChangeHandler extends EventHandler<UserChangePayload> {

    @Override
    public String getEventType() {
        return UserChangeEvent.TYPE_NAME;
    }
}

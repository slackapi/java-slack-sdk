package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.UserChangePayload;
import com.github.seratch.jslack.api.model.event.UserChangeEvent;

public abstract class UserChangeHandler extends EventHandler<UserChangePayload> {

    @Override
    public String getEventType() {
        return UserChangeEvent.TYPE_NAME;
    }
}

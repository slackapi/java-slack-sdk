package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.UserResourceGrantedPayload;
import com.github.seratch.jslack.api.model.event.UserResourceGrantedEvent;

public abstract class UserResourceGrantedHandler extends EventHandler<UserResourceGrantedPayload> {

    @Override
    public String getEventType() {
        return UserResourceGrantedEvent.TYPE_NAME;
    }
}

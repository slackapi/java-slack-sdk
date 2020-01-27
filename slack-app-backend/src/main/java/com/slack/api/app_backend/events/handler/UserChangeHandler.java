package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.UserChangeEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserChangePayload;

public abstract class UserChangeHandler extends EventHandler<UserChangePayload> {

    @Override
    public String getEventType() {
        return UserChangeEvent.TYPE_NAME;
    }
}

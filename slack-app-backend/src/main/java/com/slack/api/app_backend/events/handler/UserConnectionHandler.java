package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.UserConnectionPayload;
import com.slack.api.model.event.UserConnectionEvent;

public abstract class UserConnectionHandler extends EventHandler<UserConnectionPayload> {

    @Override
    public String getEventType() {
        return UserConnectionEvent.TYPE_NAME;
    }
}

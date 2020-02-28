package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ImOpenPayload;
import com.slack.api.model.event.ImOpenEvent;

public abstract class ImOpenHandler extends EventHandler<ImOpenPayload> {

    @Override
    public String getEventType() {
        return ImOpenEvent.TYPE_NAME;
    }
}

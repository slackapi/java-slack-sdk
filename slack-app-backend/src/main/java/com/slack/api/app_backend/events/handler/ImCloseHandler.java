package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ImClosePayload;
import com.slack.api.model.event.ImCloseEvent;

public abstract class ImCloseHandler extends EventHandler<ImClosePayload> {

    @Override
    public String getEventType() {
        return ImCloseEvent.TYPE_NAME;
    }
}

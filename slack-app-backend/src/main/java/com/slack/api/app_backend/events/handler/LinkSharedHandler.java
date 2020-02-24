package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.LinkSharedPayload;
import com.slack.api.model.event.LinkSharedEvent;

public abstract class LinkSharedHandler extends EventHandler<LinkSharedPayload> {

    @Override
    public String getEventType() {
        return LinkSharedEvent.TYPE_NAME;
    }
}

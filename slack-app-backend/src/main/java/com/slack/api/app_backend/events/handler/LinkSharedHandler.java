package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.LinkSharedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.LinkSharedPayload;

public abstract class LinkSharedHandler extends EventHandler<LinkSharedPayload> {

    @Override
    public String getEventType() {
        return LinkSharedEvent.TYPE_NAME;
    }
}

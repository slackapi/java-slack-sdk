package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AssistantThreadContextChangedPayload;
import com.slack.api.model.event.AssistantThreadContextChangedEvent;

public abstract class AssistantThreadContextChangedHandler extends EventHandler<AssistantThreadContextChangedPayload> {

    @Override
    public String getEventType() {
        return AssistantThreadContextChangedEvent.TYPE_NAME;
    }
}

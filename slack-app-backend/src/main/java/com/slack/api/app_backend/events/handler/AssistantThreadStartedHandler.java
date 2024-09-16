package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AssistantThreadStartedPayload;
import com.slack.api.model.event.AssistantThreadStartedEvent;

public abstract class AssistantThreadStartedHandler extends EventHandler<AssistantThreadStartedPayload> {

    @Override
    public String getEventType() {
        return AssistantThreadStartedEvent.TYPE_NAME;
    }
}

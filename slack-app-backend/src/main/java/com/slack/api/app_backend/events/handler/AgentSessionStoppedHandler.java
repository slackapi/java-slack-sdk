package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AgentSessionStoppedPayload;
import com.slack.api.model.event.AgentSessionStoppedEvent;

public abstract class AgentSessionStoppedHandler extends EventHandler<AgentSessionStoppedPayload> {

    @Override
    public String getEventType() {
        return AgentSessionStoppedEvent.TYPE_NAME;
    }
}

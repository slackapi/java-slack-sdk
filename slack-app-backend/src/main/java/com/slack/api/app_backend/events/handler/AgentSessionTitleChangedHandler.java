package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AgentSessionTitleChangedPayload;
import com.slack.api.model.event.AgentSessionTitleChangedEvent;

public abstract class AgentSessionTitleChangedHandler extends EventHandler<AgentSessionTitleChangedPayload> {

    @Override
    public String getEventType() {
        return AgentSessionTitleChangedEvent.TYPE_NAME;
    }
}

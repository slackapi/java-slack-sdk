package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.ReactionAddedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ReactionAddedPayload;

public abstract class ReactionAddedHandler extends EventHandler<ReactionAddedPayload> {

    @Override
    public String getEventType() {
        return ReactionAddedEvent.TYPE_NAME;
    }
}

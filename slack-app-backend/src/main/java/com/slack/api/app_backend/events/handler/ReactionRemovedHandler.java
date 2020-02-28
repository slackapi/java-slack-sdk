package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ReactionRemovedPayload;
import com.slack.api.model.event.ReactionRemovedEvent;

public abstract class ReactionRemovedHandler extends EventHandler<ReactionRemovedPayload> {

    @Override
    public String getEventType() {
        return ReactionRemovedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ReactionAddedPayload;
import com.github.seratch.jslack.api.model.event.ReactionAddedEvent;

public abstract class ReactionAddedHandler extends EventHandler<ReactionAddedPayload> {

    @Override
    public String getEventType() {
        return ReactionAddedEvent.TYPE_NAME;
    }
}

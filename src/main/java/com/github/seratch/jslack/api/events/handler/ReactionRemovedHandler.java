package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ReactionRemovedPayload;
import com.github.seratch.jslack.api.model.event.ReactionRemovedEvent;

public abstract class ReactionRemovedHandler extends EventHandler<ReactionRemovedPayload> {

    @Override
    public String getEventType() {
        return ReactionRemovedEvent.TYPE_NAME;
    }
}

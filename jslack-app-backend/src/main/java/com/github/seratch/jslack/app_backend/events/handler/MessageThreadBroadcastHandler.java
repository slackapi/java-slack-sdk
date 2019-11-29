package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MessageThreadBroadcastEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessageThreadBroadcastPayload;

public abstract class MessageThreadBroadcastHandler extends EventHandler<MessageThreadBroadcastPayload> {

    @Override
    public String getEventType() {
        return MessageThreadBroadcastEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageThreadBroadcastEvent.SUBTYPE_NAME;
    }
}

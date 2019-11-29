package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MessageChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessageChangedPayload;

public abstract class MessageChangedHandler extends EventHandler<MessageChangedPayload> {

    @Override
    public String getEventType() {
        return MessageChangedEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChangedEvent.SUBTYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MessageDeletedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessageDeletedPayload;

public abstract class MessageDeletedHandler extends EventHandler<MessageDeletedPayload> {

    @Override
    public String getEventType() {
        return MessageDeletedEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageDeletedEvent.SUBTYPE_NAME;
    }
}

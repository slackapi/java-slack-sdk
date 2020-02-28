package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChangedPayload;
import com.slack.api.model.event.MessageChangedEvent;

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

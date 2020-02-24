package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageDeletedPayload;
import com.slack.api.model.event.MessageDeletedEvent;

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

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageFileSharePayload;
import com.slack.api.model.event.MessageFileShareEvent;

public abstract class MessageFileShareHandler extends EventHandler<MessageFileSharePayload> {

    @Override
    public String getEventType() {
        return MessageFileShareEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageFileShareEvent.SUBTYPE_NAME;
    }
}

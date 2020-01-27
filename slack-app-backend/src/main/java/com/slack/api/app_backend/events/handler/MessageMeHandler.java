package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.MessageMeEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageMePayload;

public abstract class MessageMeHandler extends EventHandler<MessageMePayload> {

    @Override
    public String getEventType() {
        return MessageMeEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageMeEvent.SUBTYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.MessageBotEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessageBotPayload;

public abstract class MessageBotHandler extends EventHandler<MessageBotPayload> {

    @Override
    public String getEventType() {
        return MessageBotEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageBotEvent.SUBTYPE_NAME;
    }
}

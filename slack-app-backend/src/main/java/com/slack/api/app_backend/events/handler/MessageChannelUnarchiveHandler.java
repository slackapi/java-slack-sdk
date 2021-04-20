package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelUnarchivePayload;
import com.slack.api.model.event.MessageChannelUnarchiveEvent;

public abstract class MessageChannelUnarchiveHandler extends EventHandler<MessageChannelUnarchivePayload> {

    @Override
    public String getEventType() {
        return MessageChannelUnarchiveEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelUnarchiveEvent.SUBTYPE_NAME;
    }
}

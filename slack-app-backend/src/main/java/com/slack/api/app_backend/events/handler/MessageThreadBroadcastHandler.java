package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageThreadBroadcastPayload;
import com.slack.api.model.event.MessageThreadBroadcastEvent;

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

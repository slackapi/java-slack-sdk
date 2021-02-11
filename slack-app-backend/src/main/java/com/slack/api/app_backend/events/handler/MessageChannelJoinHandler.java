package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelJoinPayload;
import com.slack.api.model.event.MessageChannelJoinEvent;

public abstract class MessageChannelJoinHandler extends EventHandler<MessageChannelJoinPayload> {

    @Override
    public String getEventType() {
        return MessageChannelJoinEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelJoinEvent.SUBTYPE_NAME;
    }
}

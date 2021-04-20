package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelNamePayload;
import com.slack.api.model.event.MessageChannelNameEvent;

public abstract class MessageChannelNameHandler extends EventHandler<MessageChannelNamePayload> {

    @Override
    public String getEventType() {
        return MessageChannelNameEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelNameEvent.SUBTYPE_NAME;
    }
}

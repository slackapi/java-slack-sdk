package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelArchivePayload;
import com.slack.api.model.event.MessageChannelArchiveEvent;

public abstract class MessageChannelArchiveHandler extends EventHandler<MessageChannelArchivePayload> {

    @Override
    public String getEventType() {
        return MessageChannelArchiveEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelArchiveEvent.SUBTYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelConvertToPublicPayload;
import com.slack.api.model.event.MessageChannelConvertToPublicEvent;

public abstract class MessageChannelConvertToPublicHandler extends EventHandler<MessageChannelConvertToPublicPayload> {

    @Override
    public String getEventType() {
        return MessageChannelConvertToPublicEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelConvertToPublicEvent.SUBTYPE_NAME;
    }
}

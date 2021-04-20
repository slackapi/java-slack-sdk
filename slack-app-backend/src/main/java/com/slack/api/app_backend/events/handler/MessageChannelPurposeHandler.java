package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelPurposePayload;
import com.slack.api.model.event.MessageChannelPurposeEvent;

public abstract class MessageChannelPurposeHandler extends EventHandler<MessageChannelPurposePayload> {

    @Override
    public String getEventType() {
        return MessageChannelPurposeEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelPurposeEvent.SUBTYPE_NAME;
    }
}

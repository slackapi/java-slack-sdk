package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelLeavePayload;
import com.slack.api.model.event.MessageChannelLeaveEvent;

public abstract class MessageChannelLeaveHandler extends EventHandler<MessageChannelLeavePayload> {

    @Override
    public String getEventType() {
        return MessageChannelLeaveEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelLeaveEvent.SUBTYPE_NAME;
    }
}

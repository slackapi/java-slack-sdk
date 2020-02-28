package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageEkmAccessDeniedPayload;
import com.slack.api.model.event.MessageEkmAccessDeniedEvent;

public abstract class MessageEkmAccessDeniedHandler extends EventHandler<MessageEkmAccessDeniedPayload> {

    @Override
    public String getEventType() {
        return MessageEkmAccessDeniedEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageEkmAccessDeniedEvent.SUBTYPE_NAME;
    }
}

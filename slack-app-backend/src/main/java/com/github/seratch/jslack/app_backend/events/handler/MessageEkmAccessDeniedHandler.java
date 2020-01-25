package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MessageEkmAccessDeniedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessageEkmAccessDeniedPayload;

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

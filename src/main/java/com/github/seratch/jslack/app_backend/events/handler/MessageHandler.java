package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MessageEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MessagePayload;

public abstract class MessageHandler extends EventHandler<MessagePayload> {

    @Override
    public String getEventType() {
        return MessageEvent.TYPE_NAME;
    }
}

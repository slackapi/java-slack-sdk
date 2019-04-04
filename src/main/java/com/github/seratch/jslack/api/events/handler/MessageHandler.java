package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.MessagePayload;
import com.github.seratch.jslack.api.model.event.MessageEvent;

public abstract class MessageHandler extends EventHandler<MessagePayload> {

    @Override
    public String getEventType() {
        return MessageEvent.TYPE_NAME;
    }
}

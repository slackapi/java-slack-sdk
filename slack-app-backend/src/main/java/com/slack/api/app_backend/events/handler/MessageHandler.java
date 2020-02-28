package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.model.event.MessageEvent;

public abstract class MessageHandler extends EventHandler<MessagePayload> {

    @Override
    public String getEventType() {
        return MessageEvent.TYPE_NAME;
    }
}

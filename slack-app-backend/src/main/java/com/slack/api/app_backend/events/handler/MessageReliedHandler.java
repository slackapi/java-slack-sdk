package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageRepliedPayload;
import com.slack.api.model.event.MessageRepliedEvent;

public abstract class MessageReliedHandler extends EventHandler<MessageRepliedPayload> {

    @Override
    public String getEventType() {
        return MessageRepliedEvent.TYPE_NAME;
    }
}

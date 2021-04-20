package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageGroupTopicPayload;
import com.slack.api.model.event.MessageGroupTopicEvent;

public abstract class MessageGroupTopicHandler extends EventHandler<MessageGroupTopicPayload> {

    @Override
    public String getEventType() {
        return MessageGroupTopicEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageGroupTopicEvent.SUBTYPE_NAME;
    }
}

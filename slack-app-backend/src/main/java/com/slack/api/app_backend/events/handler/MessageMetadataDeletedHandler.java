package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageMetadataDeletedPayload;
import com.slack.api.model.event.MessageMetadataDeletedEvent;

public abstract class MessageMetadataDeletedHandler extends EventHandler<MessageMetadataDeletedPayload> {

    @Override
    public String getEventType() {
        return MessageMetadataDeletedEvent.TYPE_NAME;
    }
}

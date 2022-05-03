package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageMetadataUpdatedPayload;
import com.slack.api.model.event.MessageMetadataUpdatedEvent;

public abstract class MessageMetadataUpdatedHandler extends EventHandler<MessageMetadataUpdatedPayload> {

    @Override
    public String getEventType() {
        return MessageMetadataUpdatedEvent.TYPE_NAME;
    }
}

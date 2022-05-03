package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageMetadataPostedPayload;
import com.slack.api.model.event.MessageMetadataPostedEvent;

public abstract class MessageMetadataPostedHandler extends EventHandler<MessageMetadataPostedPayload> {

    @Override
    public String getEventType() {
        return MessageMetadataPostedEvent.TYPE_NAME;
    }
}

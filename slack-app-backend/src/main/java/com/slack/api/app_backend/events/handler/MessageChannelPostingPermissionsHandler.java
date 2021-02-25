package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MessageChannelPostingPermissionsPayload;
import com.slack.api.model.event.MessageChannelPostingPermissionsEvent;

public abstract class MessageChannelPostingPermissionsHandler extends EventHandler<MessageChannelPostingPermissionsPayload> {

    @Override
    public String getEventType() {
        return MessageChannelPostingPermissionsEvent.TYPE_NAME;
    }

    @Override
    public String getEventSubtype() {
        return MessageChannelPostingPermissionsEvent.SUBTYPE_NAME;
    }
}

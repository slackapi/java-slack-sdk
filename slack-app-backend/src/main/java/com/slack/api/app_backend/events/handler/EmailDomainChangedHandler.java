package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.EmailDomainChangedPayload;
import com.slack.api.model.event.EmailDomainChangedEvent;

public abstract class EmailDomainChangedHandler extends EventHandler<EmailDomainChangedPayload> {

    @Override
    public String getEventType() {
        return EmailDomainChangedEvent.TYPE_NAME;
    }
}

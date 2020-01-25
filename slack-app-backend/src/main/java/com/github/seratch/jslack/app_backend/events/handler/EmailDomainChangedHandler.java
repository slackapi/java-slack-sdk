package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.EmailDomainChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.EmailDomainChangedPayload;

public abstract class EmailDomainChangedHandler extends EventHandler<EmailDomainChangedPayload> {

    @Override
    public String getEventType() {
        return EmailDomainChangedEvent.TYPE_NAME;
    }
}

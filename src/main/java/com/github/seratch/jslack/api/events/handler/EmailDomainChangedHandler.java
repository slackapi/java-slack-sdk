package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.EmailDomainChangedPayload;
import com.github.seratch.jslack.api.model.event.EmailDomainChangedEvent;

public abstract class EmailDomainChangedHandler extends EventHandler<EmailDomainChangedPayload> {

    @Override
    public String getEventType() {
        return EmailDomainChangedEvent.TYPE_NAME;
    }
}

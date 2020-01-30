package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.CallRejectedPayload;
import com.slack.api.model.event.CallRejectedEvent;

public abstract class CallRejectedHandler extends EventHandler<CallRejectedPayload> {

    @Override
    public String getEventType() {
        return CallRejectedEvent.TYPE_NAME;
    }
}

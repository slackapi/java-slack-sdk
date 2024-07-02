package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.FunctionExecutedPayload;
import com.slack.api.model.event.FunctionExecutedEvent;

public abstract class FunctionExecutedHandler extends EventHandler<FunctionExecutedPayload> {

    @Override
    public String getEventType() {
        return FunctionExecutedEvent.TYPE_NAME;
    }
}

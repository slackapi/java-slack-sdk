package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.ImHistoryChangedPayload;
import com.slack.api.model.event.ImHistoryChangedEvent;

public abstract class ImHistoryChangedHandler extends EventHandler<ImHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return ImHistoryChangedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.ImHistoryChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.ImHistoryChangedPayload;

public abstract class ImHistoryChangedHandler extends EventHandler<ImHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return ImHistoryChangedEvent.TYPE_NAME;
    }
}

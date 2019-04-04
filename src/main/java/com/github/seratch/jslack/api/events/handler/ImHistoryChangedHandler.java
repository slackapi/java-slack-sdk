package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.ImHistoryChangedPayload;
import com.github.seratch.jslack.api.model.event.ImHistoryChangedEvent;

public abstract class ImHistoryChangedHandler extends EventHandler<ImHistoryChangedPayload> {

    @Override
    public String getEventType() {
        return ImHistoryChangedEvent.TYPE_NAME;
    }
}

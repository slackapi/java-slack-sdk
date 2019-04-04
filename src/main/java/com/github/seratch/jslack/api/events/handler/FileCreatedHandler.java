package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.FileCreatedPayload;
import com.github.seratch.jslack.api.model.event.FileCreatedEvent;

public abstract class FileCreatedHandler extends EventHandler<FileCreatedPayload> {

    @Override
    public String getEventType() {
        return FileCreatedEvent.TYPE_NAME;
    }
}

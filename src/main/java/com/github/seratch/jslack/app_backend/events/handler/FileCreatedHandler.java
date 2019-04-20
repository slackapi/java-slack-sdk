package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.FileCreatedPayload;
import com.github.seratch.jslack.api.model.event.FileCreatedEvent;

public abstract class FileCreatedHandler extends EventHandler<FileCreatedPayload> {

    @Override
    public String getEventType() {
        return FileCreatedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.FileDeletedPayload;
import com.github.seratch.jslack.api.model.event.FileDeletedEvent;

public abstract class FileDeletedHandler extends EventHandler<FileDeletedPayload> {

    @Override
    public String getEventType() {
        return FileDeletedEvent.TYPE_NAME;
    }
}

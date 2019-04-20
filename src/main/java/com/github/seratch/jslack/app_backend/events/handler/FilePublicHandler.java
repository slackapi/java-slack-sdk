package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.FilePublicPayload;
import com.github.seratch.jslack.api.model.event.FilePublicEvent;

public abstract class FilePublicHandler extends EventHandler<FilePublicPayload> {

    @Override
    public String getEventType() {
        return FilePublicEvent.TYPE_NAME;
    }
}

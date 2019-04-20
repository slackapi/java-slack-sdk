package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.FileSharedPayload;
import com.github.seratch.jslack.api.model.event.FileSharedEvent;

public abstract class FileSharedHandler extends EventHandler<FileSharedPayload> {

    @Override
    public String getEventType() {
        return FileSharedEvent.TYPE_NAME;
    }
}

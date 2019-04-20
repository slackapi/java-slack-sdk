package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.FileUnsharedPayload;
import com.github.seratch.jslack.api.model.event.FileUnsharedEvent;

public abstract class FileUnsharedHandler extends EventHandler<FileUnsharedPayload> {

    @Override
    public String getEventType() {
        return FileUnsharedEvent.TYPE_NAME;
    }
}

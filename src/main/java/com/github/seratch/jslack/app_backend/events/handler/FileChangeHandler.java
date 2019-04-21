package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.FileChangeEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.FileChangePayload;

public abstract class FileChangeHandler extends EventHandler<FileChangePayload> {

    @Override
    public String getEventType() {
        return FileChangeEvent.TYPE_NAME;
    }
}

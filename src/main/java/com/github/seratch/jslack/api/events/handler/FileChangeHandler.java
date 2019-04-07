package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.FileChangePayload;
import com.github.seratch.jslack.api.model.event.FileChangeEvent;

public abstract class FileChangeHandler extends EventHandler<FileChangePayload> {

    @Override
    public String getEventType() {
        return FileChangeEvent.TYPE_NAME;
    }
}

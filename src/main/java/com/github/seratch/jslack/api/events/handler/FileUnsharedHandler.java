package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.FileUnsharedPayload;
import com.github.seratch.jslack.api.model.event.FileUnsharedEvent;

public abstract class FileUnsharedHandler extends EventHandler<FileUnsharedPayload> {

    @Override
    public String getEventType() {
        return FileUnsharedEvent.TYPE_NAME;
    }
}

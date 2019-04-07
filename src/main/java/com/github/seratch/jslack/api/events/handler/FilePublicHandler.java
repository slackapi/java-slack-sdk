package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.FilePublicPayload;
import com.github.seratch.jslack.api.model.event.FilePublicEvent;

public abstract class FilePublicHandler extends EventHandler<FilePublicPayload> {

    @Override
    public String getEventType() {
        return FilePublicEvent.TYPE_NAME;
    }
}

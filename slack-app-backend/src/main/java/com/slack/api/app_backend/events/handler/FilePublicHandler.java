package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.FilePublicEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.FilePublicPayload;

public abstract class FilePublicHandler extends EventHandler<FilePublicPayload> {

    @Override
    public String getEventType() {
        return FilePublicEvent.TYPE_NAME;
    }
}

package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.FileDeletedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.FileDeletedPayload;

public abstract class FileDeletedHandler extends EventHandler<FileDeletedPayload> {

    @Override
    public String getEventType() {
        return FileDeletedEvent.TYPE_NAME;
    }
}

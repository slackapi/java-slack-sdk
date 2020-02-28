package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.FileSharedPayload;
import com.slack.api.model.event.FileSharedEvent;

public abstract class FileSharedHandler extends EventHandler<FileSharedPayload> {

    @Override
    public String getEventType() {
        return FileSharedEvent.TYPE_NAME;
    }
}

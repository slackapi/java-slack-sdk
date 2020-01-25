package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.FileUnsharedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.FileUnsharedPayload;

public abstract class FileUnsharedHandler extends EventHandler<FileUnsharedPayload> {

    @Override
    public String getEventType() {
        return FileUnsharedEvent.TYPE_NAME;
    }
}

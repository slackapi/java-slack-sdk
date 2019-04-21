package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.AppMentionEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.AppMentionPayload;

public abstract class AppMentionHandler extends EventHandler<AppMentionPayload> {

    @Override
    public String getEventType() {
        return AppMentionEvent.TYPE_NAME;
    }
}

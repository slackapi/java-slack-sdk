package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.AppMentionPayload;
import com.slack.api.model.event.AppMentionEvent;

public abstract class AppMentionHandler extends EventHandler<AppMentionPayload> {

    @Override
    public String getEventType() {
        return AppMentionEvent.TYPE_NAME;
    }
}

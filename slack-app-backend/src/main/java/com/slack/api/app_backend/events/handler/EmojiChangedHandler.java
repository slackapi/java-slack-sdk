package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.EmojiChangedPayload;
import com.slack.api.model.event.EmojiChangedEvent;

public abstract class EmojiChangedHandler extends EventHandler<EmojiChangedPayload> {

    @Override
    public String getEventType() {
        return EmojiChangedEvent.TYPE_NAME;
    }
}

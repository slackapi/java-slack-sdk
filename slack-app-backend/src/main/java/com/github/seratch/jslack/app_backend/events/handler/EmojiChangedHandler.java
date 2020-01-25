package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.EmojiChangedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.EmojiChangedPayload;

public abstract class EmojiChangedHandler extends EventHandler<EmojiChangedPayload> {

    @Override
    public String getEventType() {
        return EmojiChangedEvent.TYPE_NAME;
    }
}

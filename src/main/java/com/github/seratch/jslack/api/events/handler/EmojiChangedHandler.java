package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.EmojiChangedPayload;
import com.github.seratch.jslack.api.model.event.EmojiChangedEvent;

public abstract class EmojiChangedHandler extends EventHandler<EmojiChangedPayload> {

    @Override
    public String getEventType() {
        return EmojiChangedEvent.TYPE_NAME;
    }
}

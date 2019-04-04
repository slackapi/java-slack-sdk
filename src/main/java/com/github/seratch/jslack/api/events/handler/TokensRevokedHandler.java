package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.TokensRevokedPayload;
import com.github.seratch.jslack.api.model.event.TokensRevokedEvent;

public abstract class TokensRevokedHandler extends EventHandler<TokensRevokedPayload> {

    @Override
    public String getEventType() {
        return TokensRevokedEvent.TYPE_NAME;
    }
}

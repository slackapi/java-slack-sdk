package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.TokensRevokedPayload;
import com.github.seratch.jslack.api.model.event.TokensRevokedEvent;

public abstract class TokensRevokedHandler extends EventHandler<TokensRevokedPayload> {

    @Override
    public String getEventType() {
        return TokensRevokedEvent.TYPE_NAME;
    }
}

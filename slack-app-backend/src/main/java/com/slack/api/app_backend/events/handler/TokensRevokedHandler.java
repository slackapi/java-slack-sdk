package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TokensRevokedPayload;
import com.slack.api.model.event.TokensRevokedEvent;

public abstract class TokensRevokedHandler extends EventHandler<TokensRevokedPayload> {

    @Override
    public String getEventType() {
        return TokensRevokedEvent.TYPE_NAME;
    }
}

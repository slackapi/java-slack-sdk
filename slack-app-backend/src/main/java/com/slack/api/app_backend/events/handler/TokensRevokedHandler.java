package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.TokensRevokedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.TokensRevokedPayload;

public abstract class TokensRevokedHandler extends EventHandler<TokensRevokedPayload> {

    @Override
    public String getEventType() {
        return TokensRevokedEvent.TYPE_NAME;
    }
}

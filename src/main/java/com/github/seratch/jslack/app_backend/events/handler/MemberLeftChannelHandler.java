package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MemberLeftChannelPayload;
import com.github.seratch.jslack.api.model.event.MemberLeftChannelEvent;

public abstract class MemberLeftChannelHandler extends EventHandler<MemberLeftChannelPayload> {

    @Override
    public String getEventType() {
        return MemberLeftChannelEvent.TYPE_NAME;
    }
}

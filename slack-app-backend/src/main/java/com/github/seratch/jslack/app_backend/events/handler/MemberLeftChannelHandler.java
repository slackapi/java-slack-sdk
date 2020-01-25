package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.MemberLeftChannelEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MemberLeftChannelPayload;

public abstract class MemberLeftChannelHandler extends EventHandler<MemberLeftChannelPayload> {

    @Override
    public String getEventType() {
        return MemberLeftChannelEvent.TYPE_NAME;
    }
}

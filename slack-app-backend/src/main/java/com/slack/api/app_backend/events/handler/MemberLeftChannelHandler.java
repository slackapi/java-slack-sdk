package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MemberLeftChannelPayload;
import com.slack.api.model.event.MemberLeftChannelEvent;

public abstract class MemberLeftChannelHandler extends EventHandler<MemberLeftChannelPayload> {

    @Override
    public String getEventType() {
        return MemberLeftChannelEvent.TYPE_NAME;
    }
}

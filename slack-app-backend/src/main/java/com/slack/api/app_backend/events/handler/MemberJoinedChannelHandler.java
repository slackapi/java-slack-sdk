package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.MemberJoinedChannelPayload;
import com.slack.api.model.event.MemberJoinedChannelEvent;

public abstract class MemberJoinedChannelHandler extends EventHandler<MemberJoinedChannelPayload> {

    @Override
    public String getEventType() {
        return MemberJoinedChannelEvent.TYPE_NAME;
    }
}

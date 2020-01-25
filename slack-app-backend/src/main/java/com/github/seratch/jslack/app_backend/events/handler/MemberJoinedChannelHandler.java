package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.MemberJoinedChannelEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.MemberJoinedChannelPayload;

public abstract class MemberJoinedChannelHandler extends EventHandler<MemberJoinedChannelPayload> {

    @Override
    public String getEventType() {
        return MemberJoinedChannelEvent.TYPE_NAME;
    }
}

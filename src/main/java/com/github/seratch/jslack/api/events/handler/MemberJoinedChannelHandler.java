package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.MemberJoinedChannelPayload;
import com.github.seratch.jslack.api.model.event.MemberJoinedChannelEvent;

public abstract class MemberJoinedChannelHandler extends EventHandler<MemberJoinedChannelPayload> {

    @Override
    public String getEventType() {
        return MemberJoinedChannelEvent.TYPE_NAME;
    }
}

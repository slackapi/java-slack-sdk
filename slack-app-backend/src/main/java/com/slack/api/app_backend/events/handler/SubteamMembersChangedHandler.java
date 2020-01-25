package com.slack.api.app_backend.events.handler;

import com.slack.api.model.event.SubteamMembersChangedEvent;
import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.SubteamMembersChangedPayload;

public abstract class SubteamMembersChangedHandler extends EventHandler<SubteamMembersChangedPayload> {

    @Override
    public String getEventType() {
        return SubteamMembersChangedEvent.TYPE_NAME;
    }
}

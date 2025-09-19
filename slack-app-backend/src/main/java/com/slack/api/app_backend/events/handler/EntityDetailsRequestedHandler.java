
package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.EntityDetailsRequestedPayload;
import com.slack.api.model.event.EntityDetailsRequestedEvent;

public abstract class EntityDetailsRequestedHandler extends EventHandler<EntityDetailsRequestedPayload> {

    @Override
    public String getEventType() {
        return EntityDetailsRequestedEvent.TYPE_NAME;
    }
}

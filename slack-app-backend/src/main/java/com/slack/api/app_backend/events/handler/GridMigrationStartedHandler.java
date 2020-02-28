package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GridMigrationStartedPayload;
import com.slack.api.model.event.GridMigrationStartedEvent;

public abstract class GridMigrationStartedHandler extends EventHandler<GridMigrationStartedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationStartedEvent.TYPE_NAME;
    }
}

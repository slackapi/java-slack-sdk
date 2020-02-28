package com.slack.api.app_backend.events.handler;

import com.slack.api.app_backend.events.EventHandler;
import com.slack.api.app_backend.events.payload.GridMigrationFinishedPayload;
import com.slack.api.model.event.GridMigrationFinishedEvent;

public abstract class GridMigrationFinishedHandler extends EventHandler<GridMigrationFinishedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationFinishedEvent.TYPE_NAME;
    }
}

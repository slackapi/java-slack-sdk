package com.github.seratch.jslack.app_backend.events.handler;

import com.slack.api.model.event.GridMigrationFinishedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GridMigrationFinishedPayload;

public abstract class GridMigrationFinishedHandler extends EventHandler<GridMigrationFinishedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationFinishedEvent.TYPE_NAME;
    }
}

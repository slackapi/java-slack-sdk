package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GridMigrationFinishedPayload;
import com.github.seratch.jslack.api.model.event.GridMigrationFinishedEvent;

public abstract class GridMigrationFinishedHandler extends EventHandler<GridMigrationFinishedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationFinishedEvent.TYPE_NAME;
    }
}

package com.github.seratch.jslack.app_backend.events.handler;

import com.github.seratch.jslack.api.model.event.GridMigrationStartedEvent;
import com.github.seratch.jslack.app_backend.events.EventHandler;
import com.github.seratch.jslack.app_backend.events.payload.GridMigrationStartedPayload;

public abstract class GridMigrationStartedHandler extends EventHandler<GridMigrationStartedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationStartedEvent.TYPE_NAME;
    }
}

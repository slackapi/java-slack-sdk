package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GridMigrationStartedPayload;
import com.github.seratch.jslack.api.model.event.GridMigrationStartedEvent;

public abstract class GridMigrationStartedHandler extends EventHandler<GridMigrationStartedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationStartedEvent.TYPE_NAME;
    }
}

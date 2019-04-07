package com.github.seratch.jslack.api.events.handler;

import com.github.seratch.jslack.api.events.EventHandler;
import com.github.seratch.jslack.api.events.payload.GridMigrationFinishedPayload;
import com.github.seratch.jslack.api.model.event.GridMigrationFinishedEvent;

public abstract class GridMigrationFinishedHandler extends EventHandler<GridMigrationFinishedPayload> {

    @Override
    public String getEventType() {
        return GridMigrationFinishedEvent.TYPE_NAME;
    }
}

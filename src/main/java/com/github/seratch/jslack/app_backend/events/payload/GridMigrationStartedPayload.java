package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GridMigrationStartedEvent;
import lombok.Data;

import java.util.List;

@Data
public class GridMigrationStartedPayload implements EventsApiPayload<GridMigrationStartedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GridMigrationStartedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

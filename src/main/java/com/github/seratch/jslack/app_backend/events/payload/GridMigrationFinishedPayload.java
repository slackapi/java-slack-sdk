package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GridMigrationFinishedEvent;
import lombok.Data;

import java.util.List;

@Data
public class GridMigrationFinishedPayload implements EventsApiPayload<GridMigrationFinishedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GridMigrationFinishedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

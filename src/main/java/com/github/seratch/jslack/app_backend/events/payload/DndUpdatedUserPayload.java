package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.DndUpdatedUserEvent;
import lombok.Data;

import java.util.List;

@Data
public class DndUpdatedUserPayload implements EventsApiPayload<DndUpdatedUserEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private DndUpdatedUserEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

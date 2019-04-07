package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.DndUpdatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class DndUpdatedPayload implements EventsApiPayload<DndUpdatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private DndUpdatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

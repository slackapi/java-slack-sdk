package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ImCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ImCreatedPayload implements EventsApiPayload<ImCreatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ImCreatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

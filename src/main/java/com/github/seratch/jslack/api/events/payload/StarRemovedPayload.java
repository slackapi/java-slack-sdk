package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.StarRemovedEvent;
import lombok.Data;

import java.util.List;

@Data
public class StarRemovedPayload implements EventsApiPayload<StarRemovedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private StarRemovedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

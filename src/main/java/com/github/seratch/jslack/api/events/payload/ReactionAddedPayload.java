package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ReactionAddedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ReactionAddedPayload implements EventsApiPayload<ReactionAddedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ReactionAddedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

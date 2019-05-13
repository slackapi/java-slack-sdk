package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ReactionRemovedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ReactionRemovedPayload implements EventsApiPayload<ReactionRemovedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ReactionRemovedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

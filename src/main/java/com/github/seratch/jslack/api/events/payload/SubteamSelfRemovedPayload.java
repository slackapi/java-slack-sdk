package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.SubteamSelfRemovedEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubteamSelfRemovedPayload implements EventsApiPayload<SubteamSelfRemovedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private SubteamSelfRemovedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

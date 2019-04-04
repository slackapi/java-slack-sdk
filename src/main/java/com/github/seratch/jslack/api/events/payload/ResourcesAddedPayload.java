package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ResourcesAddedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ResourcesAddedPayload implements EventsApiPayload<ResourcesAddedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ResourcesAddedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

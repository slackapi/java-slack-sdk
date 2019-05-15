package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.ResourcesRemovedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ResourcesRemovedPayload implements EventsApiPayload<ResourcesRemovedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ResourcesRemovedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

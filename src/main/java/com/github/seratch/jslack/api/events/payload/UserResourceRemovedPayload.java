package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.UserResourceRemovedEvent;
import lombok.Data;

import java.util.List;

@Data
public class UserResourceRemovedPayload implements EventsApiPayload<UserResourceRemovedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private UserResourceRemovedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

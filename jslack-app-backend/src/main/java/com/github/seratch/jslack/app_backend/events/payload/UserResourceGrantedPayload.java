package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.UserResourceGrantedEvent;
import lombok.Data;

import java.util.List;

@Data
public class UserResourceGrantedPayload implements EventsApiPayload<UserResourceGrantedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private UserResourceGrantedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

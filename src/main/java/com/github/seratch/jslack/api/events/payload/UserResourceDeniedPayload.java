package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.UserResourceDeniedEvent;
import lombok.Data;

import java.util.List;

@Data
public class UserResourceDeniedPayload implements EventsApiPayload<UserResourceDeniedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private UserResourceDeniedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

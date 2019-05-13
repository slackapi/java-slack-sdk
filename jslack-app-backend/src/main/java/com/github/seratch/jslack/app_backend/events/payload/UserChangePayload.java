package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.UserChangeEvent;
import lombok.Data;

import java.util.List;

@Data
public class UserChangePayload implements EventsApiPayload<UserChangeEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private UserChangeEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.GroupLeftEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupLeftPayload implements EventsApiPayload<GroupLeftEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupLeftEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

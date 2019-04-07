package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.GroupOpenEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupOpenPayload implements EventsApiPayload<GroupOpenEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupOpenEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

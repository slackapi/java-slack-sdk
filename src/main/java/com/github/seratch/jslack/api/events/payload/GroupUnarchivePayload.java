package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.GroupUnarchiveEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupUnarchivePayload implements EventsApiPayload<GroupUnarchiveEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupUnarchiveEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

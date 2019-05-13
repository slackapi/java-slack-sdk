package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GroupDeletedEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupDeletedPayload implements EventsApiPayload<GroupDeletedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupDeletedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

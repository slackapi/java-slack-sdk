package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GroupArchiveEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupArchivePayload implements EventsApiPayload<GroupArchiveEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupArchiveEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.GroupCloseEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupClosePayload implements EventsApiPayload<GroupCloseEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private GroupCloseEvent event;
}

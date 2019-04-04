package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.SubteamMembersChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubteamMembersChangedPayload implements EventsApiPayload<SubteamMembersChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private SubteamMembersChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

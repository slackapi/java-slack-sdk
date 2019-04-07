package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.GroupHistoryChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupHistoryChangedPayload implements EventsApiPayload<GroupHistoryChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupHistoryChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

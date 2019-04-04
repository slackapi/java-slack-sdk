package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.GroupRenameEvent;
import lombok.Data;

import java.util.List;

@Data
public class GroupRenamePayload implements EventsApiPayload<GroupRenameEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private GroupRenameEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

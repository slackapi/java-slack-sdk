package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.ImHistoryChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class ImHistoryChangedPayload implements EventsApiPayload<ImHistoryChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private ImHistoryChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

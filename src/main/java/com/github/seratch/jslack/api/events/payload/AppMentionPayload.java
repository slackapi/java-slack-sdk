package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.AppMentionEvent;
import lombok.Data;

import java.util.List;

@Data
public class AppMentionPayload implements EventsApiPayload<AppMentionEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private AppMentionEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

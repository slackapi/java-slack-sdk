package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.MessageEvent;
import lombok.Data;

import java.util.List;

@Data
public class MessagePayload implements EventsApiPayload<MessageEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private MessageEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

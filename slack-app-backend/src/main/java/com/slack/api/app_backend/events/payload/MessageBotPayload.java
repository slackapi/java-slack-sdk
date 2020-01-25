package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.MessageBotEvent;
import lombok.Data;

import java.util.List;

@Data
public class MessageBotPayload implements EventsApiPayload<MessageBotEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;

    private MessageBotEvent event;
}

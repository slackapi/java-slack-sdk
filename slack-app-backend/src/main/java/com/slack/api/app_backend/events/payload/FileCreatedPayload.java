package com.slack.api.app_backend.events.payload;

import com.slack.api.model.event.FileCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileCreatedPayload implements EventsApiPayload<FileCreatedEvent> {

    private String token;
    private String enterpriseId;
    private String teamId;
    private String apiAppId;
    private String type;
    private List<String> authedUsers;
    private List<String> authedTeams;
    private String eventId;
    private Integer eventTime;
    private String eventContext;

    private FileCreatedEvent event;
}

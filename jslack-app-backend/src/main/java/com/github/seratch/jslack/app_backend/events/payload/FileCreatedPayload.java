package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.FileCreatedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileCreatedPayload implements EventsApiPayload<FileCreatedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FileCreatedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

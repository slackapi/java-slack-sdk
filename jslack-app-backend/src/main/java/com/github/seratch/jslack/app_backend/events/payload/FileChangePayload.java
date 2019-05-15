package com.github.seratch.jslack.app_backend.events.payload;

import com.github.seratch.jslack.api.model.event.FileChangeEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileChangePayload implements EventsApiPayload<FileChangeEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FileChangeEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

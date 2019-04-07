package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.FileUnsharedEvent;
import lombok.Data;

import java.util.List;

@Data
public class FileUnsharedPayload implements EventsApiPayload<FileUnsharedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private FileUnsharedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.EmailDomainChangedEvent;
import lombok.Data;

import java.util.List;

@Data
public class EmailDomainChangedPayload implements EventsApiPayload<EmailDomainChangedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private EmailDomainChangedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

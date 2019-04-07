package com.github.seratch.jslack.api.events.payload;

import com.github.seratch.jslack.api.model.event.TokensRevokedEvent;
import lombok.Data;

import java.util.List;

@Data
public class TokensRevokedPayload implements EventsApiPayload<TokensRevokedEvent> {

    private String token;
    private String teamId;
    private String apiAppId;
    private TokensRevokedEvent event;
    private String type;
    private List<String> authedUsers;
    private String eventId;
    private Integer eventTime;

}

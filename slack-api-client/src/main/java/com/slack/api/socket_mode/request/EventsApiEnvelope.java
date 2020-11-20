package com.slack.api.socket_mode.request;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsApiEnvelope implements SocketModeEnvelope {
    public static final String TYPE = "events_api";
    private final String type = TYPE;
    private String envelopeId;
    private Boolean acceptsResponsePayload;
    private JsonElement payload;
    private Integer retryAttempt;
    private String retryReason;
}

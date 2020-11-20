package com.slack.api.socket_mode.request;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class InteractiveEnvelope implements SocketModeEnvelope {
    public static final String TYPE = "interactive";
    private final String type = TYPE;
    private String envelopeId;
    private Boolean acceptsResponsePayload;
    private JsonElement payload;
    private Integer retryAttempt;
    private String retryReason;
}

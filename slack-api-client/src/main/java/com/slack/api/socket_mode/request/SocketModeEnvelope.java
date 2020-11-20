package com.slack.api.socket_mode.request;

import com.google.gson.JsonElement;

public interface SocketModeEnvelope {

    String getEnvelopeId();

    JsonElement getPayload();

    String getType();

    Boolean getAcceptsResponsePayload();

    Integer getRetryAttempt();

    String getRetryReason();

}

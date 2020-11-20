package com.slack.api.socket_mode.listener;

import com.slack.api.socket_mode.request.SocketModeEnvelope;

@FunctionalInterface
public interface EnvelopeListener<E extends SocketModeEnvelope> {

    void handle(E envelope);

}

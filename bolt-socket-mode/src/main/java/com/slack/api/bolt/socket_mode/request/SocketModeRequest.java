package com.slack.api.bolt.socket_mode.request;

import com.slack.api.bolt.request.Request;
import com.slack.api.socket_mode.request.SocketModeEnvelope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketModeRequest {
    private SocketModeEnvelope envelope;
    private Request<?> boltRequest;
}

package com.slack.api.socket_mode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapResponse implements SocketModeResponse {
    private String envelopeId;
    private Map<String, Object> payload;
}

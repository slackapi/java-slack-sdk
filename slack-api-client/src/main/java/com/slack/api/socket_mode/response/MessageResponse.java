package com.slack.api.socket_mode.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements SocketModeResponse {
    private String envelopeId;
    private MessagePayload payload;
}

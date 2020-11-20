package com.slack.api.socket_mode.request;

import lombok.Data;

@Data
public class DisconnectMessage {
    public static final String TYPE = "disconnect";
    private final String type = TYPE;
    private String reason;
    private DebugInfo debugInfo;

    @Data
    public static class DebugInfo {
        private String host;
    }
}

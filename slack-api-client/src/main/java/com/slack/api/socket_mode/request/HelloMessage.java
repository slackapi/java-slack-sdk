package com.slack.api.socket_mode.request;

import lombok.Data;

@Data
public class HelloMessage {
    public static final String TYPE = "hello";
    private final String type = TYPE;
    private Integer numConnections;
    private DebugInfo debugInfo;
    private ConnectionInfo connectionInfo;

    @Data
    public static class DebugInfo {
        private String host;
        private Integer buildNumber;
        private Integer approximateConnectionTime;
    }

    @Data
    public static class ConnectionInfo {
        private String appId;
    }
}

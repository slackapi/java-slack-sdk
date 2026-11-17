package com.slack.api.methods.response.admin.apps.mcp.servers;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAppsMcpServersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ResponseMetadata responseMetadata;
    private List<Server> servers;

    @Data
    public static class Server {
        private String appId;
        private String appName;
        private String authType;
        private String serverId;
        private String serverName;
        private String url;
    }
}

package com.slack.api.methods.response.admin.apps.mcp.servers.permissions;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAppsMcpServersPermissionsSetResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Boolean isInherited;
    private String permissionType;
    private String serverId;
    private String serverName;
    private String url;
    private List<String> usergroupIds;
    private List<String> userIds;
}

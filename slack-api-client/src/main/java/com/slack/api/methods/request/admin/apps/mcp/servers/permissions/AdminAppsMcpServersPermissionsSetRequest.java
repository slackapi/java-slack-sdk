package com.slack.api.methods.request.admin.apps.mcp.servers.permissions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.mcp.servers.permissions.set
 */
@Data
@Builder
public class AdminAppsMcpServersPermissionsSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Encoded ID of the app
     */
    private String appId;

    /**
     * Encoded ID of the MCP server
     */
    private String serverId;

    /**
     * The type of permission that defines who can use this MCP server
     * Acceptable values: `everyone`, `no_one`, `named_entities`, `named_entities_exclude`
     */
    private String permissionType;

    /**
     * List of user IDs to set for named_entities or named_entities_exclude
     */
    private List<String> userIds;

    /**
     * List of encoded usergroup IDs
     */
    private List<String> usergroupIds;

}

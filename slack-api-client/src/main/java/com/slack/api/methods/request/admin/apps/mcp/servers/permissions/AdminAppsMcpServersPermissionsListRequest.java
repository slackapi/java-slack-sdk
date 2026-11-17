package com.slack.api.methods.request.admin.apps.mcp.servers.permissions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.mcp.servers.permissions.list
 */
@Data
@Builder
public class AdminAppsMcpServersPermissionsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Encoded ID of the app
     */
    private String appId;

}

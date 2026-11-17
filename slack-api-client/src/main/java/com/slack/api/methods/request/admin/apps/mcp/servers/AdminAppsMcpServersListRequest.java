package com.slack.api.methods.request.admin.apps.mcp.servers;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.mcp.servers.list
 */
@Data
@Builder
public class AdminAppsMcpServersListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * The maximum number of items to return. Must be between 1 - 1000 both inclusive.
     */
    private Integer limit;

    /**
     * Set `cursor` to `next_cursor` returned by the previous call to list items in the next page
     */
    private String cursor;

}

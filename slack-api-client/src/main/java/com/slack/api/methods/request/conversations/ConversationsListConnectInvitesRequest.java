package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsListConnectInvitesRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Maximum number of invites to return
     * Default: 100
     */
    private Integer count;

    /**
     * Set to next_cursor returned by previous call to list items in subsequent page
     */
    private String cursor;

    /**
     * Encoded team id for the workspace to retrieve invites for, required if org token is used
     */
    private String teamId;

}

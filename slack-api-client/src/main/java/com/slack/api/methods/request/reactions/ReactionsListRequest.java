package com.slack.api.methods.request.reactions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `reactions:read`
     */
    private String token;

    /**
     * Show reactions made by this user. Defaults to the authed user.
     */
    private String user;

    /**
     * If true always return the complete reaction list.
     */
    private boolean full;

    private Integer count;

    private Integer page;

    private Integer limit;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.apps.restrict
 */
@Data
@Builder
public class AdminAppsRestrictRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The id of the app to approve.
     */
    private String appId;

    /**
     * The id of the request to approve.
     */
    private String requestId;

    /**
     * Workspace Id
     */
    private String teamId;

}

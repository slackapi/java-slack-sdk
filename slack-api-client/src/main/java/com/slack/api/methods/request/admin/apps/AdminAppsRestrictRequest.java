package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.restrict
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
     * The ID of the enterprise to approve the app on
     */
    private String enterpriseId;

    /**
     * The ID of the workspace to approve the app on
     */
    private String teamId;

}

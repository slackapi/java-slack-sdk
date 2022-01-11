package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.apps.requests.cancel
 */
@Data
@Builder
public class AdminAppsRequestsCancelRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The id of the request to cancel.
     */
    private String requestId;

    /**
     * The ID of the enterprise where this request belongs
     */
    private String enterpriseId;

    /**
     * The ID of the workspace where this request belongs
     */
    private String teamId;

}

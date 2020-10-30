package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.apps.clearResolution
 */
@Data
@Builder
public class AdminAppsClearResolutionRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The id of the app whose resolution you want to clear/undo.
     */
    private String appId;

    /**
     * The enterprise to clear the app resolution from
     */
    private String enterpriseId;

    /**
     * The workspace to clear the app resolution from
     */
    private String teamId;

}

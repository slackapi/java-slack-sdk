package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.admin.AppConfig;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.apps.config.set
 */
@Data
@Builder
public class AdminAppsConfigSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private String appId;
    private AppConfig.DomainRestrictions domainRestrictions;
    private String workflowAuthStrategy;
}

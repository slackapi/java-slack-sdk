package com.slack.api.methods.request.admin.apps;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Uninstall an app from one or many workspaces, or an entire enterprise organization.
 * <p>
 * https://docs.slack.dev/reference/methods/admin.apps.uninstall
 */
@Data
@Builder
public class AdminAppsUninstallRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * The ID of the app to uninstall.
     */
    private String appId;

    /**
     * The enterprise to completely uninstall the application from (across all workspaces).
     * With an org-level token, this or team_ids is required.
     */
    private String enterpriseId;

    /**
     * Ds of the teams to uninstall from (max 100). With an org-level token, this or enterprise_id is required.
     */
    private List<String> teamIds;

}

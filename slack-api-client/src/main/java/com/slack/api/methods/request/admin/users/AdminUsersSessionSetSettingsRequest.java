package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.users.session.setSettings
 */
@Data
@Builder
public class AdminUsersSessionSetSettingsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The list of user IDs to apply the session settings for.
     */
    private List<String> userIds;

    /**
     * Terminate the session when the client—either the desktop app or a browser window—is closed.
     */
    private Boolean desktopAppBrowserQuit;

    /**
     * The session duration, in seconds. The minimum value is 28800,
     * which represents 8 hours; the max value is 315569520 or 10 years (that's a long Slack session).
     */
    private Integer duration;
}

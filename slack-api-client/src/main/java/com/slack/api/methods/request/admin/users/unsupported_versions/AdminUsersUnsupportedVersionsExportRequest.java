package com.slack.api.methods.request.admin.users.unsupported_versions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.unsupportedVersions.export
 */
@Data
@Builder
public class AdminUsersUnsupportedVersionsExportRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Unix timestamp of the date of past or upcoming end of support cycles.
     * If not provided will include all announced end of support cycles.
     */
    private Integer dateEndOfSupport;

    /**
     * Unix timestamp of a date to start looking for user sessions.
     * If not provided will start six months ago.
     */
    private Integer dateSessionsStarted;
}

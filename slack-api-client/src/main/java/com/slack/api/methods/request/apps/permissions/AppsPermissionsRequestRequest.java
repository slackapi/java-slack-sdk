package com.slack.api.methods.request.apps.permissions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * On August 24, 2021, legacy workspace apps were retired. Workspace apps were part of a brief developer preview we elected to not fully release. Since October 2018, existing workspace apps have remained functional but on August 24, 2021 workspace apps will be retired and no longer function.
 * - https://docs.slack.dev/changelog/2021-01-workspace-apps-retiring-the-platform-graveyard-in-aug-2021
 */
@Deprecated
@Data
@Builder
public class AppsPermissionsRequestRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `none`
     */
    private String token;

    /**
     * Token used to trigger the permissions API
     */
    private String triggerId;

    /**
     * A comma separated list of scopes to request for
     */
    private List<String> scopes;

}
package com.slack.api.methods.request.apps.permissions.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * On August 24, 2021, legacy workspace apps were retired. Workspace apps were part of a brief developer preview we elected to not fully release. Since October 2018, existing workspace apps have remained functional but on August 24, 2021 workspace apps will be retired and no longer function.
 * - https://api.slack.com/changelog/2021-03-workspace-apps-to-retire-in-august-2021
 */
@Deprecated
@Data
@Builder
public class AppsPermissionsUsersListRequest implements SlackApiRequest {

    private String token;

    /**
     * Paginate through collections of data by setting the cursor parameter to
     * a next_cursor attribute returned by a previous request's response_metadata.
     * Default value fetches the first "page" of the collection. See pagination for more detail.
     */
    private String cursor;

    /**
     * The maximum number of items to return.
     */
    private Integer limit;

}
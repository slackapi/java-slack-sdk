package com.slack.api.methods.request.apps.permissions.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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
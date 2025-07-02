package com.slack.api.methods.request.stars;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/stars.list
 */
@Data
@Builder
public class StarsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `stars:read`
     */
    private String token;

    /**
     * Number of items to return per page.
     */
    private Integer count;

    /**
     * Parameter for pagination. Set cursor equal to the next_cursor attribute returned
     * by the previous request's response_metadata. This parameter is optional,
     * but pagination is mandatory: the default value simply fetches
     * the first "page" of the collection. See pagination for more details.
     */
    private String cursor;

    /**
     * The maximum number of items to return.
     * Fewer than the requested number of items may be returned, even if the end of the list hasn't been reached.
     */
    private Integer limit;

    /**
     * Page number of results to return.
     */
    private Integer page;

    /**
     * encoded team id to list stars in, required if org token is used
     */
    private String teamId;

}
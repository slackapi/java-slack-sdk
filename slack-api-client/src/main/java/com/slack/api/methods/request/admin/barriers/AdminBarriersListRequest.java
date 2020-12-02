package com.slack.api.methods.request.admin.barriers;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.barriers.list
 */
@Data
@Builder
public class AdminBarriersListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Set cursor to next_cursor returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * The maximum number of items to return. Must be between 1 - 1000 both inclusive.
     * Default: 100
     */
    private Integer limit;

}

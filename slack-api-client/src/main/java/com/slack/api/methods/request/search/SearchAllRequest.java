package com.slack.api.methods.request.search;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/search.all
 */
@Data
@Builder
public class SearchAllRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `search:read`
     */
    private String token;

    /**
     * Search query. May contains booleans, etc.
     */
    private String query;

    /**
     * Return matches sorted by either `score` or `timestamp`.
     */
    private String sort;

    /**
     * Change sort direction to ascending (`asc`) or descending (`desc`).
     */
    private String sortDir;

    /**
     * Pass a value of `true` to enable query highlight markers (see below).
     */
    private boolean highlight;

    private Integer count;

    private Integer page;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
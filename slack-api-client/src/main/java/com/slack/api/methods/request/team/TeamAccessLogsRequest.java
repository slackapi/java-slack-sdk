package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/team.accessLogs
 */
@Data
@Builder
public class TeamAccessLogsRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `admin`
     */
    private String token;

    /**
     * End of time range of logs to include in results (inclusive).
     */
    private Integer before;

    private Integer count;

    private Integer page;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

    /**
     * The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the list hasn't been reached.
     * If specified, result is returned using a cursor-based approach instead of a classic one.
     */
    private Integer limit;

    /**
     * Parameter for pagination. Set cursor equal to the next_cursor attribute returned
     * by the previous request's response_metadata. This parameter is optional,
     * but pagination is mandatory: the default value simply fetches the first "page" of the collection.
     */
    private String cursor;

}
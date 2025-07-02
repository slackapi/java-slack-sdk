package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/team.billableInfo
 */
@Data
@Builder
public class TeamBillableInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `admin`
     */
    private String token;

    /**
     * A user to retrieve the billable information for. Defaults to all users.
     */
    private String user;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

    /**
     * Set cursor to next_cursor returned by previous call,
     * to indicate from where you want to list next page of users list.
     * Default value fetches the first page.
     */
    private String cursor;

    /**
     * The maximum number of items to return.
     */
    private Integer limit;
}
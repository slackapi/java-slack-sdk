package com.slack.api.methods.request.team.external_teams;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/team.externalTeams.list
 */
@Data
@Builder
public class TeamExternalTeamsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Status of the connected team.
     * Value should be one of "CONNECTED", "DISCONNECTED", "BLOCKED", and "IN_REVIEW"
     */
    private String connectionStatusFilter;

    /**
     * Encoded team ID to retrieve next page of connected teams, if absent will return first page
     */
    private String cursor;

    /**
     * The maximum number of items to return per page (default: 20)
     */
    private Integer limit;

    /**
     * Filters connected orgs by Slack Connect pref override(s)
     */
    private List<String> slackConnectPrefFilter;

    /**
     * Direction to sort in asc or desc
     * Value should be one of "asc" and "desc"
     */
    private String sortDirection;

    /**
     * Name of the parameter that we are sorting by
     * <p>
     * Value should be one of "team_name", "last_active_timestamp", and "connection_status"
     */
    private String sortField;

    /**
     * Shows connected orgs which are connected on a specified encoded workspace ID
     */
    private List<String> workspaceFilter;
}

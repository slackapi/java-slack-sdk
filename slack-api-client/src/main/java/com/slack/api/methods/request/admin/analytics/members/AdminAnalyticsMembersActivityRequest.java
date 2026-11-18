package com.slack.api.methods.request.admin.analytics.members;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.analytics.members.activity
 */
@Data
@Builder
public class AdminAnalyticsMembersActivityRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * The day to report on, as `YYYY-MM-DD` in UTC. Defaults to the most recent day with data.
     */
    private String date;

    /**
     * Workspace ID to scope the results to. With an organization-level token, omit it for organization-wide results.
     */
    private String teamId;

    /**
     * Member types to include: `regular_members`, `single_channel_guests`, `multi_channel_guests`, `frontline_members`,
     * `org_owners`, `org_admins`, `workspace_owners`, `workspace_admins`, or `invited`. Omit to include every member type.
     */
    private List<String> memberTypes;

    /**
     * User IDs to return. Up to 500 IDs. Omit to return every member.
     */
    private List<String> userIds;

    /**
     * Set `cursor` to `next_cursor` returned by the previous call to list items in the next page.
     */
    private String cursor;

    /**
     * Maximum number of members to return. Max allowed is 500.
     */
    private Integer limit;

}

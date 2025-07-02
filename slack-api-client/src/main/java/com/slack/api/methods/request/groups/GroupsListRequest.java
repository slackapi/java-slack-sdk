package com.slack.api.methods.request.groups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class GroupsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `groups:read`
     */
    private String token;

    /**
     * Exclude the `members` from each `group`
     */
    private boolean excludeMembers;

    /**
     * Don't return archived private channels.
     */
    private boolean excludeArchived;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
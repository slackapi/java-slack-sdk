package com.slack.api.methods.request.groups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class GroupsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `groups:write`
     */
    private String token;

    /**
     * Name of private channel to create
     */
    private String name;

    /**
     * Whether to return errors on invalid channel name instead of modifying it to meet the specified criteria.
     */
    private boolean validate;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
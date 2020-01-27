package com.slack.api.methods.request.groups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class GroupsKickRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `groups:write`
     */
    private String token;

    /**
     * Private channel to remove user from.
     */
    private String channel;

    /**
     * User to remove from private channel.
     */
    private String user;

}
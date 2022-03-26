package com.slack.api.methods.request.usergroups.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/usergroups.users.list
 */
@Data
@Builder
public class UsergroupsUsersListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `usergroups:read`
     */
    private String token;

    /**
     * The encoded ID of the User Group to update.
     */
    private String usergroup;

    /**
     * Allow results that involve disabled User Groups.
     */
    private boolean includeDisabled;

    /**
     * encoded team id where the user group exists, required if org token is used
     */
    private String teamId;

}
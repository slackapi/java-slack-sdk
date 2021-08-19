package com.slack.api.methods.request.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

// https://api.slack.com/methods/usergroups.disable
@Data
@Builder
public class UsergroupsDisableRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `usergroups:write`
     */
    private String token;

    /**
     * The encoded ID of the User Group to disable.
     */
    private String usergroup;

    /**
     * Include the number of users in the User Group.
     */
    private boolean includeCount;

    /**
     * encoded team id where the user group exists, required if org token is used
     */
    private String teamId;

}
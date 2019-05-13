package com.github.seratch.jslack.api.methods.request.usergroups.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsergroupUsersUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `usergroups:write`
     */
    private String token;

    /**
     * The encoded ID of the User Group to update.
     */
    private String usergroup;

    /**
     * A comma separated string of encoded user IDs that represent the entire list of users for the User Group.
     */
    private List<String> users;

    /**
     * Include the number of users in the User Group.
     */
    private boolean includeCount;

}
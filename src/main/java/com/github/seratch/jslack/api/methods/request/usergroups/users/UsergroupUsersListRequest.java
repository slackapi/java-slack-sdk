package com.github.seratch.jslack.api.methods.request.usergroups.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupUsersListRequest implements SlackApiRequest {

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

}
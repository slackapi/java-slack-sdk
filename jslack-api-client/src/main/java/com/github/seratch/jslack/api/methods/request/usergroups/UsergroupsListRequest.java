package com.github.seratch.jslack.api.methods.request.usergroups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `usergroups:read
     */
    private String token;

    /**
     * Include disabled User Groups.
     */
    private boolean includeDisabled;

    /**
     * Include the number of users in each User Group
     */
    private boolean includeCount;

    /**
     * Include the list of users for each User Group.
     */
    private boolean includeUsers;

}
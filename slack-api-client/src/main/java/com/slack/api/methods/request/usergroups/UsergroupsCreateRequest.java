package com.slack.api.methods.request.usergroups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

// https://api.slack.com/methods/usergroups.create
@Data
@Builder
public class UsergroupsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `usergroups:write`
     */
    private String token;

    /**
     * A name for the User Group. Must be unique among User Groups.
     */
    private String name;

    /**
     * A mention handle. Must be unique among channels, users and User Groups.
     */
    private String handle;

    /**
     * A short description of the User Group.
     */
    private String description;

    /**
     * A comma separated string of encoded channel IDs for which the User Group uses as a default.
     */
    private List<String> channels;

    /**
     * Include the number of users in each User Group.
     */
    private boolean includeCount;

    /**
     * encoded team id where the user group exists, required if org token is used
     */
    private String teamId;

}
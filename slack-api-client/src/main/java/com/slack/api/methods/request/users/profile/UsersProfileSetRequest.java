package com.slack.api.methods.request.users.profile;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.User;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/users.profile.set
 */
@Data
@Builder
public class UsersProfileSetRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users.profile:write`
     */
    private String token;

    /**
     * ID of user to change. This argument may only be specified by team admins on paid teams.
     */
    private String user;

    /**
     * Collection of key:value pairs presented as a URL-encoded JSON hash.
     */
    private User.Profile profile;

    /**
     * Name of a single key to set. Usable only if profile is not passed.
     */
    private String name;

    /**
     * Value to set a single key to. Usable only if profile is not passed.
     */
    private String value;
}
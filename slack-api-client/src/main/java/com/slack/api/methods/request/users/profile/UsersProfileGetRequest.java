package com.slack.api.methods.request.users.profile;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/users.profile.get
 */
@Data
@Builder
public class UsersProfileGetRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users.profile:read`
     */
    private String token;

    /**
     * User to retrieve profile info for
     */
    private String user;

    /**
     * Include labels for each ID in custom profile fields
     */
    private boolean includeLabels;

}
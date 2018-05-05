package com.github.seratch.jslack.api.methods.request.users.profile;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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
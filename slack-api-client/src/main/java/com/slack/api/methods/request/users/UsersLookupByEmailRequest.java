package com.slack.api.methods.request.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/users.lookupByEmail
 */
@Data
@Builder
public class UsersLookupByEmailRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:read.email`
     */
    private String token;

    /**
     * An email address belonging to a user in the workspace
     */
    private String email;

}
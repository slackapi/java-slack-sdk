package com.slack.api.methods.request.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersGetPresenceRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:read`
     */
    private String token;

    /**
     * User to get presence info on. Defaults to the authed user.
     */
    private String user;

}
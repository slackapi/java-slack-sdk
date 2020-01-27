package com.slack.api.methods.request.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersSetPresenceRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:write`
     */
    private String token;

    /**
     * Either `auto` or `away`
     */
    private String presence;

}
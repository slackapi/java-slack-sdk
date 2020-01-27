package com.slack.api.methods.request.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersIdentityRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `identity.basic`
     */
    private String token;

}
package com.github.seratch.jslack.api.methods.request.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users:read`
     */
    private String token;

    /**
     * User to get info on
     */
    private String user;

    /**
     * Set this to `true` to receive the locale for this user. Defaults to `false`
     */
    private boolean includeLocale;

}
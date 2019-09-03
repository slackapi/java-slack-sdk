package com.github.seratch.jslack.api.methods.request.admin.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.session.reset
 */
@Data
@Builder
public class AdminUsersSessionResetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. (admin.users:write)
     */
    private String token;

    /**
     * The ID of the user to wipe sessions for
     */
    private String userId;

    /**
     * Only expire mobile sessions (default: false)
     */
    private boolean mobileOnly;

    /**
     * Only expire web sessions (default: false)
     */
    private boolean webOnly;

}
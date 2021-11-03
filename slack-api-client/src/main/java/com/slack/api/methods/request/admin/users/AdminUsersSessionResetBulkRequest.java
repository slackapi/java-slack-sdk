package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.users.session.resetBulk
 */
@Data
@Builder
public class AdminUsersSessionResetBulkRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. (admin.users:write)
     */
    private String token;

    /**
     * The ID of the user to wipe sessions for
     */
    private List<String> userIds;

    /**
     * Only expire mobile sessions (default: false)
     */
    private boolean mobileOnly;

    /**
     * Only expire web sessions (default: false)
     */
    private boolean webOnly;

}
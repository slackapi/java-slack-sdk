package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/admin.users.session.invalidate
 */
@Data
@Builder
public class AdminUsersSessionInvalidateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. (admin.users:write)
     */
    private String token;

    /**
     *
     */
    private String sessionId;

    /**
     * ID of the team that the session belongs to
     */
    private String teamId;

}
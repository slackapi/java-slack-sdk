package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.users.session.clearSettings
 */
@Data
@Builder
public class AdminUsersSessionClearSettingsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The IDs of users you'd like to clear session settings for.
     */
    private List<String> userIds;
}

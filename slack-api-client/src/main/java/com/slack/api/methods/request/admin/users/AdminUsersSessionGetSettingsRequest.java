package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.users.session.getSettings
 */
@Data
@Builder
public class AdminUsersSessionGetSettingsRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The IDs of users you'd like to fetch session settings for.
     * Note: if a user does not have any active sessions, they will not be returned in the response.
     */
    private List<String> userIds;
}

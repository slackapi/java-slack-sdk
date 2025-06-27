package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.users.assign
 */
@Data
@Builder
public class AdminUsersAssignRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * Workspace Id.
     */
    private String teamId;

    /**
     * The ID of the user to add to the workspace.
     */
    private String userId;

    /**
     * Comma separated values of channel IDs to add user in the new workspace.
     */
    private List<String> channelIds;

    /**
     * True if user should be added to the workspace as a guest.
     */
    private boolean isRestricted;

    /**
     * True if user should be added to the workspace as a single-channel guest.
     */
    private boolean isUltraRestricted;

}

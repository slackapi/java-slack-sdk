package com.slack.api.methods.request.admin.invite_requests;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.inviteRequests.approve
 */
@Data
@Builder
public class AdminInviteRequestsApproveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * ID of the request to invite.
     */
    private String inviteRequestId;

    /**
     * ID for the workspace where the invite request was made.
     */
    private String teamId;

}

package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/conversations.approveSharedInvite
 */
@Data
@Builder
public class ConversationsApproveSharedInviteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * ID of the shared channel invite to approve
     */
    private String inviteId;

    /**
     * The team or enterprise id of the other party involved in the invitation you are approving
     */
    private String targetTeam;

}

package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.declineSharedInvite
 */
@Data
@Builder
public class ConversationsDeclineSharedInviteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * ID of the Slack Connect invite to decline. Subscribe to the shared_channel_invite_accepted event
     * to receive IDs of Slack Connect channel invites that have been accepted and are awaiting approval.
     */
    private String inviteId;

    /**
     * The team or enterprise id of the other party involved in the invitation you are declining
     */
    private String targetTeam;

}

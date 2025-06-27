package com.slack.api.methods.request.conversations.request_shared_invite;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/conversations.requestSharedInvite.deny
 */
@Data
@Builder
public class ConversationsRequestSharedInviteDenyRequest implements SlackApiRequest {

    private String token;

    /**
     * ID of the requested shared channel invite to deny.
     */
    private String inviteId;

    /**
     * Optional message explaining why the request to invite was denied.
     */
    private String message;
}

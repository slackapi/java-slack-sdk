package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/conversations.externalInvitePermissions.set
 */
@Data
@Builder
public class ConversationsExternalInvitePermissionsSetRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Type of action to be taken: upgrade or downgrade
     * Value should be one of "upgrade" and "downgrade"
     */
    private String action;

    /**
     * The channel ID to change external invite permissions for
     */
    private String channel;

    /**
     * The encoded team ID of the target team. Must be in the specified channel.
     */
    private String targetTeam;

}

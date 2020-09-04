package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.conversations.invite
 */
@Data
@Builder
public class AdminConversationsInviteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel that the users will be invited to.
     */
    private String channelId;

    /**
     * The users to invite.
     */
    private List<String> userIds;

}

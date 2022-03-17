package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConversationsInviteSharedRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     * Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * ID of the channel on your team that you'd like to share
     */
    private String channel;

    /**
     * Optional email to receive this invite. Either emails or user_ids must be provided.
     */
    private List<String> emails;

    /**
     * Optional boolean on whether invite is to a external limited member. Defaults to true.
     */
    private Boolean externalLimited;

    /**
     * Optional user_id to receive this invite. Either emails or user_ids must be provided.
     */
    private List<String> userIds;

}

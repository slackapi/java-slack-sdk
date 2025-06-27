package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
/**
 * https://docs.slack.dev/reference/methods/conversations.invite
 */
@Builder
public class ConversationsInviteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * The ID of the public or private channel to invite user(s) to.
     */
    private String channel;

    /**
     * A comma separated list of user IDs. Up to 30 users may be listed.
     */
    private List<String> users;

    /**
     * When set to `true` and multiple user IDs are provided, continue inviting the valid ones while disregarding invalid IDs. Defaults to `false`.
     */
    private Boolean force;

}

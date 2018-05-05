package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
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

}

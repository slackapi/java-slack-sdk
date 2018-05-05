package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `chat:write`
     */
    private String token;

    /**
     * Timestamp of the message to be deleted.
     */
    private String ts;

    /**
     * Channel containing the message to be deleted.
     */
    private String channel;

    /**
     * Pass true to delete the message as the authed user with `chat:write:user` scope.
     * [Bot users](/bot-users) in this context are considered authed users.
     * If unused or false, the message will be deleted with `chat:write:bot` scope.
     */
    private boolean asUser;
}
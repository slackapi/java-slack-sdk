package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/conversations.open
 */
@Data
@Builder
public class ConversationsOpenRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * Resume a conversation by supplying an `im` or `mpim`'s ID. Or provide the `users` field instead.
     */
    private String channel;

    /**
     * Boolean, indicates you want the full IM channel definition in the response.
     */
    private boolean returnIm;

    /**
     * Comma separated lists of users. If only one user is included, this creates a 1:1 DM.
     * The ordering of the users is preserved whenever a multi-person direct message is returned.
     * Supply a `channel` when not supplying `users`.
     */
    private List<String> users;

}

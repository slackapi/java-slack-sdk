package com.slack.api.methods.request.admin.conversations;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.conversations.rename
 */
@Data
@Builder
public class AdminConversationsRenameRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The channel to rename.
     */
    private String channelId;

    /**
     * The new channel name.
     */
    private String name;

}

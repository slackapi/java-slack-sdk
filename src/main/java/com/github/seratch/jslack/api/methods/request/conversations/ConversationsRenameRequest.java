package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsRenameRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:write`
     */
    private String token;

    /**
     * ID of conversation to rename
     */
    private String channel;

    /**
     * New name for conversation.
     * <p>
     * Conversation names can only contain lowercase letters, numbers, hyphens, and underscores, and must
     * be 21 characters or less. We will validate the submitted channel name and modify it to meet the above
     * criteria. When calling this method, we recommend storing the channel's name value that is returned
     * in the response.
     */
    private String name;

}
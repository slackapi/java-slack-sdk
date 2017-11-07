package com.github.seratch.jslack.api.methods.request.conversations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsRenameRequest {
    
    private String token;
    private String channel;
    
    /**
     * Conversation names can only contain lowercase letters, numbers, hyphens, and underscores, and must
     * be 21 characters or less. We will validate the submitted channel name and modify it to meet the above
     * criteria. When calling this method, we recommend storing the channel's name value that is returned
     * in the response.
     */
    private String name;
}

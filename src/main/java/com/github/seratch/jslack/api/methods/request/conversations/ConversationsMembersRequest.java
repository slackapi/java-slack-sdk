package com.github.seratch.jslack.api.methods.request.conversations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsMembersRequest {
    
    private String token;
    private String channel;
    private String cursor;
    private Integer limit;
    
}

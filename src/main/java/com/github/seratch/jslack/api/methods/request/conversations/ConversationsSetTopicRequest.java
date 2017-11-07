package com.github.seratch.jslack.api.methods.request.conversations;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsSetTopicRequest {
    
    private String token;
    private String channel;
    private String topic;
}

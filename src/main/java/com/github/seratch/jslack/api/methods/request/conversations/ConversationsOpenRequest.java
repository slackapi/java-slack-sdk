package com.github.seratch.jslack.api.methods.request.conversations;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsOpenRequest {
    
    private String token;
    private String channel;
    private boolean returnIm;
    private List<String> users;
}

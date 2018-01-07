package com.github.seratch.jslack.api.methods.request.conversations;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConversationsOpenRequest {

    private String token;
    private String channel;
    private boolean returnIm;
    private List<String> users;
}

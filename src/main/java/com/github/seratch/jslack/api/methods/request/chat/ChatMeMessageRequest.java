package com.github.seratch.jslack.api.methods.request.chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMeMessageRequest {

    private String token;
    private String channel;
    private String text;
}
package com.github.seratch.jslack.api.methods.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDeleteRequest {

    private String token;
    private String ts;
    private String channel;
    private boolean asUser;
}
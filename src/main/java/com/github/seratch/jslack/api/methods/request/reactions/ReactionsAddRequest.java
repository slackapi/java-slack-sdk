package com.github.seratch.jslack.api.methods.request.reactions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionsAddRequest {

    private String token;
    private String name;
    private String file;
    private String fileComment;
    private String channel;
    private String timestamp;
}
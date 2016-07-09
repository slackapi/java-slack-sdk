package com.github.seratch.jslack.api.methods.request.stars;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsAddRequest {

    private String token;
    private String file;
    private String fileComment;
    private String channel;
    private String timestamp;
}
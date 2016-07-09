package com.github.seratch.jslack.api.methods.request.pins;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinsRemoveRequest {

    private String token;
    private String channel;
    private String file;
    private String fileComment;
    private String timestamp;
}
package com.github.seratch.jslack.api.methods.request.pins;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinsRemoveRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private String file;
    private String fileComment;
    private String timestamp;
}
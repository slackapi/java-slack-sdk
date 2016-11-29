package com.github.seratch.jslack.api.methods.request.stars;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsRemoveRequest implements SlackApiRequest {

    private String token;
    private String file;
    private String fileComment;
    private String channel;
    private String timestamp;
}
package com.github.seratch.jslack.api.methods.request.mpim;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpimMarkRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private String ts;
}
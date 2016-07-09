package com.github.seratch.jslack.api.methods.request.mpim;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpimMarkRequest {

    private String token;
    private String channel;
    private String ts;
}
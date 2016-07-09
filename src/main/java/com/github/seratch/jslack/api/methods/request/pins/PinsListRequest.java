package com.github.seratch.jslack.api.methods.request.pins;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PinsListRequest {

    private String token;
    private String channel;
}
package com.github.seratch.jslack.api.methods.request.im;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImMarkRequest {

    private String token;
    private String channel;
    private String ts;
}
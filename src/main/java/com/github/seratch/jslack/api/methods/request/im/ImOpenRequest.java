package com.github.seratch.jslack.api.methods.request.im;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImOpenRequest {

    private String token;
    private String user;
    private boolean returnIm;
}
package com.github.seratch.jslack.api.methods.request.im;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImListRequest {

    private String token;
}
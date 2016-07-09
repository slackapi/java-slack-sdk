package com.github.seratch.jslack.api.methods.request.stars;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsListRequest {

    private String token;
    private Integer count;
    private Integer page;
}
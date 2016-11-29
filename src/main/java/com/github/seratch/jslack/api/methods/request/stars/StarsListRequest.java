package com.github.seratch.jslack.api.methods.request.stars;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarsListRequest implements SlackApiRequest {

    private String token;
    private Integer count;
    private Integer page;
}
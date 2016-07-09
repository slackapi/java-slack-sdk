package com.github.seratch.jslack.api.methods.request.im;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImHistoryRequest {

    private String token;
    private String channel;
    private String latest;
    private String oldest;
    private Integer inclusive;
    private Integer count;
    private Integer unreads;
}
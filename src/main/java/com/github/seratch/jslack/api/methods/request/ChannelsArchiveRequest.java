package com.github.seratch.jslack.api.methods.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelsArchiveRequest {

    private String token;
    private String channel;
}
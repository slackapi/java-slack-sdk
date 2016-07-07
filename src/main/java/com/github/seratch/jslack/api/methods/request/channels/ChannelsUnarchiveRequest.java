package com.github.seratch.jslack.api.methods.request.channels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelsUnarchiveRequest {

    private String token;
    private String channel;
}
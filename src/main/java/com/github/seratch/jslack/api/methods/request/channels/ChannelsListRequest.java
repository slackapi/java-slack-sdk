package com.github.seratch.jslack.api.methods.request.channels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelsListRequest {

    private String token;
    private Integer excludeArchived;
}
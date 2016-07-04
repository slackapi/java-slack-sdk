package com.github.seratch.jslack.api.methods.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelsRenameRequest {

    private String token;
    private String channel;
    private String name;
}
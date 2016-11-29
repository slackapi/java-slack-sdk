package com.github.seratch.jslack.api.methods.request.reactions;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReactionsListRequest implements SlackApiRequest {

    private String token;
    private String user;
    private boolean full;
    private Integer count;
    private Integer page;
}
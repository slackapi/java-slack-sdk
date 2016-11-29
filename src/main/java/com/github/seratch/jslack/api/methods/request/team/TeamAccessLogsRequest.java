package com.github.seratch.jslack.api.methods.request.team;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamAccessLogsRequest implements SlackApiRequest {

    private String token;
    private Integer count;
    private Integer page;
}
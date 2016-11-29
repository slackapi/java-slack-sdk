package com.github.seratch.jslack.api.methods.request.team;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamIntegrationLogsRequest implements SlackApiRequest {

    private String token;
    private String serviceId;
    private String user;
    private String changeType;
    private Integer count;
    private Integer page;
}
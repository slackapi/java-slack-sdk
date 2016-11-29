package com.github.seratch.jslack.api.methods.request.team.profile;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamProfileGetRequest implements SlackApiRequest {

    private String token;
    private String visibility;
}
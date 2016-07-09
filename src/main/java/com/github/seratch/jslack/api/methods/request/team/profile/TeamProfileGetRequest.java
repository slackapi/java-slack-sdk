package com.github.seratch.jslack.api.methods.request.team.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamProfileGetRequest {

    private String token;
    private String visibility;
}